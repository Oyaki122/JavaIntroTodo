package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.entity.Taskset;
import com.todo.repository.TasksetRepository;

@Service
public class TasksetService {
  @Autowired
  private TasksetRepository tasksetRepository;

  public List<Taskset> findById(long id) {
    var res = tasksetRepository.findById(id);
    if (res.isPresent()) {
      return List.of(res.get());
    } else {
      return List.of();
    }
  }

  public List<Taskset> findByUsergroupId(long usergroupId) {
    return tasksetRepository.findByUsergroupId(usergroupId);
  }

  public void save(Taskset taskset) {
    tasksetRepository.save(taskset);
  }
}
