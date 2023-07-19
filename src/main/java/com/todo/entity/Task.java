package com.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "Tasks")
public class Task extends TaskBase {
  @ManyToMany
  // @JoinTable(name = "share", joinColumns = @JoinColumn(name="user_id"),
  // referencedColumnName="id"
  // inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName="id"))
  private List<MUser> sharedUsers = new ArrayList<MUser>();

  @ManyToOne
  private MUser createUser = new MUser();
}
