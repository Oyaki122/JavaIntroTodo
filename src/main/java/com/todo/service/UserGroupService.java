package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.entity.Usergroup;
import com.todo.repository.UsergroupRepository;

import com.todo.entity.UsergroupPermission;
import com.todo.repository.UsergroupPermissionRepository;

@Service
public class UserGroupService {
  @Autowired
  private UsergroupRepository userGroupRepository;
  private UsergroupPermissionRepository userGroupPermissionRepository;

  public List<Usergroup> findByUserId(int userId) {
    var userInPermission = userGroupPermissionRepository.findByUserId(userId);
    List<UsergroupPermission> validUserGroups = List.of();
    for (var i : userInPermission) {
      if (i.getPermission() > 0) {
        validUserGroups.add(i);
      }
    }
    return userGroupRepository.findByid(userId);
  }

}
