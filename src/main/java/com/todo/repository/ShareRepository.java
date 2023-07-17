package com.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.entity.Share;
import com.todo.entity.ShareId;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, ShareId> {
  public List<Share> findByUserId(int user_id);

  public List<Share> findByTaskId(int task_id);
}
