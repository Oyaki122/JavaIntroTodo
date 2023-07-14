package com.todo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.todo.entity.DoneTask;
import com.todo.service.DoneTaskService;
import com.todo.entity.Task;
import com.todo.service.TaskService;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;

import com.todo.schema.EditTaskSchema;

@RestController
public class DoneTaskcontroller {
  @Autowired
  private DoneTaskService doneTaskService;

  @Autowired
  private TaskService taskService;

  @GetMapping("/all-donetask")
  public List<DoneTask> tasks() {
    return doneTaskService.findAll();
  }

  @Data
  public class CreateTaskResponse {
    private long id;
  }

  @RequestMapping(value = "/donetask/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public Boolean update(@RequestBody EditTaskSchema req, @PathVariable("id") Long id) {
    var searched = doneTaskService.findById(id);
    if (searched.isEmpty()) {
      return false;
    }
    var task = searched.get();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    task.setDue_date(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setUpdated_at(LocalDateTime.now());

    doneTaskService.save(task);
    return true;
  }

  @DeleteMapping("/donetask/{id}")
  public Boolean delete(@PathVariable("id") Long id) {
    doneTaskService.delete(id);
    return true;
  }

  @PostMapping("/donetask/{id}/undone")
  public Boolean undone(@PathVariable("id") Long id) {
    var searched = doneTaskService.findById(id);
    if (searched.isEmpty()) {
      return false;
    }
    var donetask = searched.get();
    donetask.setUpdated_at(LocalDateTime.now());

    var task = new Task();
    task.setTitle(donetask.getTitle());
    task.setDescription(donetask.getDescription());
    task.setDue_date(donetask.getDue_date());
    task.setPriority(donetask.getPriority());
    task.setCreated_at(donetask.getCreated_at());
    task.setUpdated_at(donetask.getUpdated_at());

    taskService.save(task);
    doneTaskService.delete(id);
    return true;
  }
}
