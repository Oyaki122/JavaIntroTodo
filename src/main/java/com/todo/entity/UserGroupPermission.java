package com.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity
@Data
@Table(name = "UserGroupPermissions")
@IdClass(UserGroupPermissionId.class)
public class UserGroupPermission {
  @Id
  @Column(name = "usergourp_id")
  private int usergroupId;

  @Id
  @Column(name = "user_id")
  private int userId;

  @Column(name = "permission")
  private int permission;

  @Column(name = "created_at")
  private String created_at;

  @Column(name = "updated_at")
  private String updated_at;

}
