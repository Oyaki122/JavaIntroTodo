package com.todo.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.todo.service.ShareService;
import com.todo.schema.EditShareSchema;
import com.todo.entity.Share;

@RestController
public class ShareController {
  @Autowired
  private ShareService shareService;

  @PutMapping("/share")
  public void share(@RequestBody EditShareSchema req) {
    var share = new Share();
    share.setUserId(req.getUserId());
    share.setTaskId(req.getTaskId());
    shareService.save(share);
  }

}
