package com.todo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.UserGroupPermission;
import com.todo.entity.UserGroupPermissionId;

@Repository
public interface UsergroupPermissionRepository extends JpaRepository<UserGroupPermission, UserGroupPermissionId> {
  List<UserGroupPermission> findByUsergroup_id(int usergroup_id);

  List<UserGroupPermission> findByUser_id(int user_id);
}
