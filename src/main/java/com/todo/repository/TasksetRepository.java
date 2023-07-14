package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.Taskset;

import java.util.List;

public interface TasksetRepository extends JpaRepository<Taskset, Long> {
  public List<Taskset> findByUsergroupId(long usergroupId);
}
