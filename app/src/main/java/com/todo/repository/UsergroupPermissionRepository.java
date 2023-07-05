package com.todo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.UsergroupPermission;

@Repository
public interface UsergroupPermissionRepository extends JpaRepository<UsergroupPermission, Long> {

}
