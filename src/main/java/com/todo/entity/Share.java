package com.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@Table(name = "Shares")
@IdClass(ShareId.class)
public class Share {
  @Id
  @Column(name = "user_id")
  private long userId;

  @Id
  @Column(name = "task_id")
  private long taskId;
}
