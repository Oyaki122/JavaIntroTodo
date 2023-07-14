package com.todo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.todo.service.TaskService;
import com.todo.entity.Task;

import java.util.List;
import java.time.LocalDateTime;

import com.todo.schema.TaskEditSchema;

@RestController
public class TaskController {
  @Autowired
  private TaskService taskService;

  @GetMapping("/task/all")
  public List<Task> tasks() {
    return taskService.findAll();
  }

  @RequestMapping(value = "/task/edit", method = RequestMethod.POST, consumes = "application/json")
  public Boolean create(@RequestBody TaskEditSchema req) {

    Task task = new Task();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    task.setDue_date(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setCreated_at(LocalDateTime.now());
    task.setUpdated_at(LocalDateTime.now());

    taskService.save(task);
    return true;
  }

  @DeleteMapping("/task/delete")
  public Boolean delete(@RequestParam Long id) {
    taskService.delete(id);
    return true;
  }
}
