package com.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.entity.DoneTask;
import com.todo.repository.DoneTaskRepository;

@Service
public class DoneTaskService {
  @Autowired
  private DoneTaskRepository taskRepository;

  public List<DoneTask> findAll() {
    return taskRepository.findAll();
  }

  public Optional<DoneTask> findById(Long id) {
    return taskRepository.findById(id);
  }

  public void save(DoneTask task) {
    taskRepository.save(task);
  }

  public void delete(Long id) {
    taskRepository.deleteById(id);
  }
}