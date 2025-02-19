package com.kaphack.smart_flow_builder.service;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.dto.SmartResponse;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import com.kaphack.smart_flow_builder.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
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

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class OpenAIFlowService implements ISmartFlowService {

    private final OpenAiChatModel   chatModel;
    private final MessageRepository messageRepository;
    private final MessageService    messageService;

    public List<Message> getCoversationList(SmartFlowRequestDto reqDto) {
        List<Message> messageList = new ArrayList<>();
        if (reqDto.isNewSession()) {
            var message = com.kaphack.smart_flow_builder.entity.Message.builder()
                    .sessionId(reqDto.getSessionId())
                    .message(GeneralConstants.SYSTEM_TO_ASSISTANT_MESSAGE + "Flow Id for this session: " + reqDto.getFlowId())
                    .role(MessageType.SYSTEM)
                    .build();
            messageRepository.save(message);
            messageList.add(new SystemMessage(message.getMessage()));
            message = com.kaphack.smart_flow_builder.entity.Message.builder()
                    .sessionId(reqDto.getSessionId())
                    .message("Well, what do you need built?")
                    .role(MessageType.ASSISTANT)
                    .build();
            messageRepository.save(message);
            messageList.add(new AssistantMessage(message.getMessage()));
        } else {
            messageList = messageService.getPastConversation(reqDto.getSessionId());
        }
        return messageList;
    }

    public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws Exception {
        String sessionId = reqDto.getSessionId();
        List<Message> messageList = getCoversationList(reqDto);
        messageRepository.save(
                com.kaphack.smart_flow_builder.entity.Message.builder()
                        .sessionId(sessionId)
                        .message(reqDto.getPromptText())
                        .role(MessageType.USER)
                        .build()
        );

        if (StringUtils.isNotNullOrEmpty(reqDto.getInputFlowJson())) {
            reqDto.setPromptText(reqDto.getPromptText() + "\n\n" + reqDto.getInputFlowJson());
        }
        if (StringUtils.isNotNullOrEmpty(reqDto.getPromptImage())) {
            var useMessage = new UserMessage(reqDto.getPromptText(), new Media(MimeTypeUtils.IMAGE_PNG, new URL(reqDto.getPromptImage())));
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
        log.info("Prompt: {}", prompt);
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
