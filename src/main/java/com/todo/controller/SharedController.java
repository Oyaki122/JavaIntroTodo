package com.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.todo.service.TaskService;
import com.todo.service.UserService;
import com.todo.entity.Task;
import com.todo.entity.DoneTask;
import com.todo.service.DoneTaskService;

import java.util.List;
import java.util.NoSuchElementException;
import java.time.LocalDateTime;

import lombok.Data;

import com.todo.schema.EditTaskSchema;

@Controller
public class SharedController {
  @Autowired
  private TaskService taskService;

  @Autowired
  private DoneTaskService doneTaskService;

  @Autowired
  private UserService userService;

  @Data
  public class CreateTaskResponse {
    private long id;
  }

  @GetMapping("/shared")
  public ModelAndView shared(@AuthenticationPrincipal UserDetails user) {
    ModelAndView mav = new ModelAndView("task/shared");

    var loginUser = userService.findByEmail(user.getUsername())
        .orElseThrow(() -> new NoSuchElementException("No user found with email: " + user.getUsername()));

    List<Task> tasks = loginUser.getSharedTasks();
    mav.addObject("tasks", tasks);
    return mav;
  }

  @GetMapping("/shared/{id}")
  public ModelAndView task(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails user) {
    var loginUser = userService.findByEmail(user.getUsername())
        .orElseThrow(() -> new NoSuchElementException("No user found with email: " + user.getUsername()));
    var isShared = loginUser.getSharedTasks().stream().anyMatch(task -> task.getId().equals(id));

    if (!isShared) {
      throw new NoSuchElementException("No task found with id: " + id);
    }

    ModelAndView mav = new ModelAndView("task/sharedDetail");
    Task task = taskService.findById(id).orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
    mav.addObject("task", task);
    mav.addObject("sharedUsers", task.getSharedUsers());

    return mav;
  }

  @RequestMapping(value = "/shared/{id}", method = RequestMethod.PUT, consumes = "application/json")
  public Boolean update(@RequestBody EditTaskSchema req, @PathVariable("id") Long id) {
    var searched = taskService.findById(id);
    if (searched.isEmpty()) {
      return false;
    }
    Task task = searched.get();
    task.setTitle(req.getTitle());
    task.setDescription(req.getDescription());
    task.setDueDate(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setUpdated_at(LocalDateTime.now());

    taskService.save(task);
    return true;
  }

  @PostMapping("/shared/{id}/done")
  public String done(@PathVariable("id") Long id) {
    var searched = taskService.findById(id);
    if (searched.isEmpty()) {
      return "redirect:/shared";
    }
    Task task = searched.get();
    DoneTask doneTask = new DoneTask();
    doneTask.setTitle(task.getTitle());
    doneTask.setDescription(task.getDescription());
    doneTask.setDueDate(task.getDueDate());
    doneTask.setPriority(task.getPriority());
    doneTask.setCreated_at(task.getCreated_at());
    doneTask.setUpdated_at(task.getUpdated_at());

    doneTaskService.save(doneTask);
    taskService.delete(id);
    return "redirect:/shared";
  }
}
