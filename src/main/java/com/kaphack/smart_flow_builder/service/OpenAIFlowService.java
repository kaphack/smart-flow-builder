package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.record.SmartResponse;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class OpenAIFlowService implements ISmartFlowService {

  private final OpenAiChatModel chatModel;
  private final ObjectMapper objectMapper;
  private final MessageRepository messageRepository;
  private final MessageService messageService;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws Exception {
    List<Message> messageList = new ArrayList<>();
    String sessionId = reqDto.getSessionId();
    if (StringUtils.isNullOrEmpty(sessionId)) {
      sessionId = UUID.randomUUID().toString();
      var message = com.kaphack.smart_flow_builder.entity.Message.builder()
          .sessionId(sessionId)
          .message("Well, what do you need built?")
          .role(MessageType.SYSTEM)
          .build();
      messageRepository.save(message);
      messageList.add(new SystemMessage(message.getMessage()));
    } else {
      messageList = messageService.getPastConversation(sessionId);
    }
    messageRepository.save(
        com.kaphack.smart_flow_builder.entity.Message.builder()
            .sessionId(sessionId)
            .message(reqDto.getPromptText())
            .role(MessageType.USER)
            .build()
    );

    reqDto.setPromptText(reqDto.getPromptText() + "\n\n" + reqDto.getInputFlowJson());
    if (StringUtils.isNotNullOrEmpty(reqDto.getPromptImage())) {
      var useMessage = new UserMessage(reqDto.getPromptText(),
          new Media(MimeTypeUtils.IMAGE_PNG, new URL(reqDto.getPromptImage())));
      messageList.add(useMessage);
    } else {
      var useMessage = new UserMessage(reqDto.getPromptText());
      messageList.add(useMessage);
    }
    var beanOutputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
    OpenAiChatOptions options = OpenAiChatOptions.builder()
        .temperature(1.0)
        .model(OpenAiApi.ChatModel.GPT_4_O_MINI)
        .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, beanOutputConverter.getJsonSchema()))
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

}
