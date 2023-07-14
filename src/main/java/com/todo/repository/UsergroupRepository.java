package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.entity.Usergroup;

import java.util.List;

@Repository
public interface UsergroupRepository extends JpaRepository<Usergroup, Long> {
  public List<Usergroup> findByid(long userId);
}
