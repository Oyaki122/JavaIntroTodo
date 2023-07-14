package com.todo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.UsergroupPermission;
import com.todo.entity.UsergroupPermissionId;

@Repository
public interface UsergroupPermissionRepository extends JpaRepository<UsergroupPermission, UsergroupPermissionId> {
  List<UsergroupPermission> findByUsergroupId(int usergroup_id);

  List<UsergroupPermission> findByUserId(int user_id);
}
