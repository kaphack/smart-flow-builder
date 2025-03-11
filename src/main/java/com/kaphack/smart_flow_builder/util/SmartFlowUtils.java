package com.kaphack.smart_flow_builder.util;

import com.kaphack.smart_flow_builder.constant.GeneralConstants;
import com.kaphack.smart_flow_builder.dto.SmartFlowRequestDto;
import com.kaphack.smart_flow_builder.repository.MessageRepository;
import com.kaphack.smart_flow_builder.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SmartFlowUtils {

  private MessageRepository messageRepository;
  private MessageService    messageService;

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


}
