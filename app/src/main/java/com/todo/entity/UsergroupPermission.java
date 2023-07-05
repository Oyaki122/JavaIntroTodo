package com.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usergroupPermission")
public class UsergroupPermission {

  @Column(name = "usergourp_id")
  private int usergroup_id;

  @Column(name = "user_id")
  private int user_id;

  @Column(name = "permission")
  private int permission;

  @Column(name = "created_at")
  private String created_at;

  @Column(name = "updated_at")
  private String updated_at;

}