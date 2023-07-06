package com.todo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserGroups")
public class Usergroup {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "logo")
  private String logo;

  @Column(name = "isPersonal")
  private boolean isPersonal;

  @Column(name = "created_at")
  private String created_at;

  @Column(name = "updated_at")
  private String updated_at;

}
