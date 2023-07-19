package com.todo.controller;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import com.todo.service.TaskService;
import com.todo.service.UserService;
import com.todo.entity.Task;
import com.todo.entity.DoneTask;
import com.todo.entity.MUser;
import com.todo.service.DoneTaskService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.security.Principal;
import java.time.LocalDateTime;

import lombok.Data;

import com.todo.schema.EditTaskSchema;

@Controller
public class TaskController {
  @Autowired
  private TaskService taskService;

  @Autowired
  private DoneTaskService doneTaskService;

  @Autowired
  private UserService userService;

  @GetMapping("/all-task")
  public List<Task> tasks() {
    return taskService.findAll();
  }

  @Data
  public class CreateTaskResponse {
    private long id;
  }


@RequestMapping(value = "/task", method = RequestMethod.POST)
public String create(@ModelAttribute Task task, RedirectAttributes redirectAttrs) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetail = (UserDetails) auth.getPrincipal();
    
    Optional<MUser> optUser = userService.findByEmail(userDetail.getUsername());
    if (optUser.isPresent()) {
        MUser user = optUser.get();
        task.setCreated_at(LocalDateTime.now());
        task.setUpdated_at(LocalDateTime.now());
        task.setCreateUser(user);
        taskService.save(task);
        redirectAttrs.addFlashAttribute("successMessage", "Task successfully created.");
        return "redirect:/task";
    } else {
        redirectAttrs.addFlashAttribute("errorMessage", "User not found.");
        return "redirect:/error";
    }
    
}
  

  @GetMapping("/task/{id}")
  public ModelAndView task(@PathVariable("id") Long id) {
    ModelAndView mav = new ModelAndView("task/taskDetail");
    Task task = taskService.findById(id).orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
    mav.addObject("task", task);
    mav.addObject("sharedUsers", task.getSharedUsers());

    return mav;
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
    task.setDueDate(req.getDue_date());
    task.setPriority(req.getPriority());
    task.setUpdated_at(LocalDateTime.now());

    taskService.save(task);
    return true;
  }


  @DeleteMapping("/task/{id}")
  public String delete(@PathVariable("id") Long id) {
    taskService.delete(id);
    return "redirect:/task";
  }

  @PostMapping("/task/{id}/done")
  public String done(@PathVariable("id") Long id) {
    var searched = taskService.findById(id);
    if (searched.isEmpty()) {
      return "redirect:/task";
    }
    Task task = searched.get();
    DoneTask doneTask = new DoneTask();
    doneTask.setTitle(task.getTitle());
    doneTask.setDescription(task.getDescription());
    doneTask.setDueDate(task.getDueDate());
    doneTask.setPriority(task.getPriority());
    doneTask.setCreated_at(task.getCreated_at());
    doneTask.setUpdated_at(task.getUpdated_at());
    doneTask.setCreateUser(task.getCreateUser());
    doneTaskService.save(doneTask);
    taskService.delete(id);
    return "redirect:/task";
  }

  @GetMapping("/task/{id}/edit")
  public ModelAndView edit(@PathVariable("id") Long id, Model model) {
      var searched = taskService.findById(id);
      if (searched.isEmpty()) {
          throw new TaskNotFoundException();
      }
      model.addAttribute("task", searched.get());
      return new ModelAndView("task/editTask", model.asMap());
  }
  
  @PostMapping("/task/{id}/share")
  public String share(@PathVariable("id") Long id,
      @RequestParam(name = "user_email", required = true) String user_email) {
    var task = taskService.findById(id).orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
    var share = task.getSharedUsers();
    var user = userService.findByEmail(user_email)
        .orElseThrow(() -> new NoSuchElementException("No user found with email: " + user_email));
    share.add(user);
    task.setSharedUsers(share);
    taskService.save(task);
    return "redirect:/task/" + id;
  }

  @DeleteMapping("/task/{id}/share/{user_id}")
  public String unshare(@PathVariable("id") Long id,
      @PathVariable("user_id") Long user_id) {
    var task = taskService.findById(id).orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
    var share = task.getSharedUsers();
    share.removeIf(s -> s.getId() == user_id);
    task.setSharedUsers(share);
    taskService.save(task);
    return "redirect:/task/" + id;
  }

  @PutMapping("/task/{id}")
  public String update(@PathVariable("id") Long id, @Validated @ModelAttribute Task task, BindingResult bindingResult) {
      var searched = taskService.findById(id);
      if (searched.isEmpty()) {
          throw new TaskNotFoundException();
      }
      Task searchedTask = searched.get();
      task.setId(id);
      task.setCreated_at(searchedTask.getCreated_at());
      taskService.update(task);
      return "redirect:/task/" + id;
  }

  @GetMapping("/task/new")
  public String showCreateTaskForm(Model model) {
    model.addAttribute("task", new Task());
      return "task/createTask";
  }
    @GetMapping("/shared")
  public String sharedtask(Model model, Principal principal) {
    String email = principal.getName();
    Optional<MUser> optUser = userService.findByEmail(email);
    if (optUser.isPresent()) {
      MUser user = optUser.get();
      List<Task> tasks = taskService.findBySharedUserId(user.getId());
      model.addAttribute("tasks", tasks);
      return "task/shared-top";
    } else {
      return "redirect:/error";
    }
  }
  
}
