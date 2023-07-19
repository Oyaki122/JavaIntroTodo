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
public class SharedDoneController {
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

  @GetMapping("/sharedDone")
  public ModelAndView shared(@AuthenticationPrincipal UserDetails user) {
    ModelAndView mav = new ModelAndView("task/shared");

    var loginUser = userService.findByEmail(user.getUsername())
        .orElseThrow(() -> new NoSuchElementException("No user found with email: " + user.getUsername()));

    List<Task> tasks = loginUser.getSharedTasks();
    mav.addObject("tasks", tasks);
    return mav;
  }

  @GetMapping("/sharedDone/{id}")
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

  @RequestMapping(value = "/sharedDone/{id}", method = RequestMethod.PUT, consumes = "application/json")
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

  @PostMapping("/sharedDone/{id}/undone")
  public String done(@PathVariable("id") Long id) {
    var searched = doneTaskService.findById(id);
    if (searched.isEmpty()) {
      return "redirect:/shared";
    }
    DoneTask task = searched.get();
    Task newTask = new Task();
    newTask.setTitle(task.getTitle());
    newTask.setDescription(task.getDescription());
    newTask.setDueDate(task.getDueDate());
    newTask.setPriority(task.getPriority());
    newTask.setCreated_at(task.getCreated_at());
    newTask.setUpdated_at(task.getUpdated_at());

    taskService.save(newTask);
    doneTaskService.delete(id);
    return "redirect:/sharedDone";
  }
}
