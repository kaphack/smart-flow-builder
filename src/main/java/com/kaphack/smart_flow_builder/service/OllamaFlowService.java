package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.dto.SmartResponse;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import com.kaphack.smart_flow_builder.util.MediaInputUtils;
import com.kaphack.smart_flow_builder.util.SmartFlowUtils;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class OllamaFlowService implements ISmartFlowService {

  private final OllamaChatModel   chatModel;
  private final MessageRepository messageRepository;
  private final MediaInputUtils   mediaInputUtils;
  private final SmartFlowUtils    smartFlowUtils;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) {
    String sessionId = reqDto.getSessionId();
    List<Message> messageList = smartFlowUtils.getCoversationList(reqDto);
    messageRepository.save(
        com.kaphack.smart_flow_builder.entity.Message.builder()
            .sessionId(sessionId)
            .message(reqDto.getPromptText())
            .inputFlowJson(reqDto.getInputFlowJson())
            .role(MessageType.USER)
            .build()
    );

    if (StringUtils.isNotNullOrEmpty(reqDto.getPromptImage())) {
      var media = new Media(MimeTypeUtils.IMAGE_PNG, mediaInputUtils.getResourceFromImageUrl(reqDto.getPromptImage()));
      var useMessage = new UserMessage("Describe the flow mentioned in the image", media);
//      messageList.add(useMessage);
//      Prompt visionPrompt = new Prompt(useMessage, OllamaOptions.builder()
//          .temperature(1.0)
//          .model(OllamaModel.LLAVA).build());
//      String visionOutput = chatModel.call(visionPrompt).getResult().getOutput().getContent();
//      useMessage = new UserMessage(reqDto.getPromptText() + "\n" + visionOutput);
      messageList.add(useMessage);
    } else {
      var useMessage = new UserMessage(reqDto.getPromptText());
      messageList.add(useMessage);
    }
    messageRepository.save(
        com.kaphack.smart_flow_builder.entity.Message.builder()
            .sessionId(sessionId)
            .message(reqDto.getPromptText())
            .role(MessageType.USER)
            .build()
    );

    reqDto.setPromptText(reqDto.getPromptText() + "\n\n" + reqDto.getInputFlowJson());
    var beanOutputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
    OllamaOptions options = OllamaOptions.builder()
        .temperature(0.3)
        .model(reqDto.getModel())
        .format(beanOutputConverter.getJsonSchemaMap())
        .functionCallbacks(FunctionCallbackService.functionCallbackList)
        .build();
    Prompt prompt = new Prompt(messageList, options);
    String output = chatModel.call(prompt).getResult().getOutput().getContent();

    com.kaphack.smart_flow_builder.entity.Message assistantMessage = com.kaphack.smart_flow_builder.entity.Message.builder()
        .sessionId(sessionId)
        .message(output)
        .role(MessageType.ASSISTANT)
        .build();
    messageRepository.save(assistantMessage);
    return ResponseEntity.ok(new SmartResponse(sessionId, assistantMessage));
  }

  @Override
  public ResponseEntity<?> generateJavascript(SmartFlowRequestDto reqDto) {
    return null;
  }

}
