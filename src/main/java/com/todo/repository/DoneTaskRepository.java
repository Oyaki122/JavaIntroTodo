package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.entity.DoneTask;

@Repository
public interface DoneTaskRepository extends JpaRepository<DoneTask, Long> {

}
