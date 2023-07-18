package com.todo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todo.entity.Task;
import com.todo.service.TaskService;
import com.todo.service.UserService;
import com.todo.auth.SimpleLoginUser;

@Controller
@RequestMapping("/")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private TaskService taskService;

  @GetMapping("/signup")
  public String getSignup(@ModelAttribute("user") SignupForm form) {
    return "user/signup";
  }

  @PostMapping("/signup")
  public String signup(@Validated @ModelAttribute("user") SignupForm form, BindingResult result,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      userService.setUser(form);
      return "user/signup";
    }

    userService.setUser(form);

    redirectAttributes.addFlashAttribute("successMessage", "アカウントの登録が完了しました");
    return "redirect:/";
  }

  @GetMapping("/index")
  public String getIndex(Model model, @AuthenticationPrincipal SimpleLoginUser loginUser) {
    List<Task> tasks = taskService.findAll();
    model.addAttribute("tasks", tasks);
    return "index";
  }

  @RequestMapping("/login")
  public String loginForm() {
    return "user/login";
  }

  @GetMapping("/task")
  public String user(Model model) {
    List<Task> tasks = taskService.findAll();
    model.addAttribute("tasks", tasks);
    return "task/user-top";
  }
<<<<<<< HEAD

  @GetMapping("/task/{id}")
  public ModelAndView task(@PathVariable("id") Long id) {
    ModelAndView mav = new ModelAndView("task/taskDetail");
    Task task = taskService.findById(id).orElseThrow(() -> new NoSuchElementException("No task found with id: " + id));
    mav.addObject("task", task);
    return mav;
  }
=======
>>>>>>> 563e893c9ebd298ac2c202dd577e1a1de7fedf92
}