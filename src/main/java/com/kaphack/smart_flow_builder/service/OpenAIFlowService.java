package com.kaphack.smart_flow_builder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.record.ModelOutputFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class OpenAIFlowService implements ISmartFlowService {

  private final OpenAiChatModel chatModel;
  private final ObjectMapper objectMapper;

  public ResponseEntity<?> getSmartFlow(SmartFlowRequestDto reqDto) throws JsonProcessingException {
    String sessionId = reqDto.getSessionId();
    List<Message> ollamaMessageList = new ArrayList<>();
    ollamaMessageList.add(new SystemMessage("Well, what do you need built?"));
    ollamaMessageList.add(new UserMessage(reqDto.getPromptText()));
    BeanOutputConverter beanOutputConverter = new BeanOutputConverter<>(ModelOutputFormat.class);
//    new PromptTemplate(beanOutputConverter.getFormat(), beanOutputConverter.getJsonSchema()).;
    OpenAiChatOptions options = OpenAiChatOptions.builder()
        .temperature(1.0)
        .model(OpenAiApi.ChatModel.GPT_4_O_MINI)
        .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, beanOutputConverter.getJsonSchema()))
        .build();
    Prompt prompt = new Prompt(ollamaMessageList, options);
    String output = chatModel.call(prompt).getResult().getOutput().getContent();
    ModelOutputFormat responseFromLLM = objectMapper.readValue(output, ModelOutputFormat.class);

    ModelOutputFormat updatedResponseFromLLM = new ModelOutputFormat(
        responseFromLLM.status(),
        responseFromLLM.flowJson(),
//        responseFromLLM.reply(),
        sessionId
    );
    return ResponseEntity.ok(updatedResponseFromLLM);
  }

}
