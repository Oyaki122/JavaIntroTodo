package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.entity.Share;
import com.todo.repository.ShareRepository;

@Service
public class ShareService {
  @Autowired
  private ShareRepository shareRepository;

  public List<Share> findByUserId(int user_id) {
    return shareRepository.findByUserId(user_id);
  }

  public List<Share> findByTaskId(int task_id) {
    return shareRepository.findByTaskId(task_id);
  }

  public void save(Share share) {
    shareRepository.save(share);
  }

  public void delete(Share share) {
    shareRepository.delete(share);
  }
}
