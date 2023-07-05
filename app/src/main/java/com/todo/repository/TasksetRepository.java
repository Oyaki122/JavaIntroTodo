package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.Taskset;

public interface TasksetRepository extends JpaRepository<Taskset, Long> {

}
