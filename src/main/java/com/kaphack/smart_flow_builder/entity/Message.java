package com.kaphack.smart_flow_builder.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "smart_flow_conversation")
@Data
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "session_id", nullable = false)
  private String sessionId;

  @Column(name = "message", nullable = false, columnDefinition = "TEXT")
  private String message;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;


  public enum Role {
    SYSTEM, USER, ASSISTANT
  }
}
