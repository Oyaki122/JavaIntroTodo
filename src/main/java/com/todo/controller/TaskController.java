package com.todo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.todo.service.TaskService;
import com.todo.entity.Task;
import com.todo.entity.DoneTask;
import com.todo.service.DoneTaskService;

import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;

import com.todo.schema.EditTaskSchema;

@RestController
public class TaskController {
  @Autowired
  private TaskService taskService;

  @Autowired
  private DoneTaskService doneTaskService;

  @GetMapping("/all-task")
  public List<Task> tasks() {
    return taskService.findAll();
  }

  @Data
  public class CreateTaskResponse {
    private long id;
  }

  @RequestMapping(value = "/task", method = RequestMethod.POST, consumes = "application/json")
  public CreateTaskResponse create(@RequestBody EditTaskSchema req) {
    Task task = new Task();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    task.setDue_date(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setCreated_at(LocalDateTime.now());
    task.setUpdated_at(LocalDateTime.now());

    var res = new CreateTaskResponse();
    res.setId(taskService.save(task));
    return res;
  }

  @RequestMapping(value = "/task/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public Boolean update(@RequestBody EditTaskSchema req, @PathVariable("id") Long id) {
    var searched = taskService.findById(id);
    if (searched.isEmpty()) {
      return false;
    }
    Task task = searched.get();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    task.setDue_date(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setUpdated_at(LocalDateTime.now());

    taskService.save(task);
    return true;
  }

  @DeleteMapping("/task/{id}")
  public Boolean delete(@PathVariable("id") Long id) {
    taskService.delete(id);
    return true;
  }

  @PostMapping("/task/{id}/done")
  public Boolean done(@PathVariable("id") Long id) {
    var searched = taskService.findById(id);
    if (searched.isEmpty()) {
      return false;
    }
    Task task = searched.get();
    DoneTask doneTask = new DoneTask();
    doneTask.setTitle(task.getTitle());
    doneTask.setDescription(task.getDescription());
    doneTask.setDue_date(task.getDue_date());
    doneTask.setPriority(task.getPriority());
    doneTask.setCreated_at(task.getCreated_at());
    doneTask.setUpdated_at(task.getUpdated_at());

    doneTaskService.save(doneTask);
    taskService.delete(id);
    return true;
  }
}
