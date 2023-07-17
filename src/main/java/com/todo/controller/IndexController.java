package com.todo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.todo.entity.MUser;
import com.todo.service.AccountService;
import com.todo.service.UserService;

public class IndexController {
  private final UserService userService;

  public IndexController(UserService userService) {
    this.userService = userService;
  }

  // @GetMapping(value = "/")
  // public String index(@ModelAttribute("signup") SignupForm signupForm, Model
  // model) {
  // List<MUser> userList = userService.findAll();
  // model.addAttribute("users", userList);
  // return "index";
  // }
  @GetMapping("/")
  public String index() {
    return "/alltask";
  }
}
