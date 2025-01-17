package com.kaphack.smart_flow_builder.repository;

import com.kaphack.smart_flow_builder.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

  List<Message> findBySessionId(String sessionId);

}