
package com.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.expression.spel.SpelEvaluationException;
// import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.List;

import com.todo.service.TaskService;
import com.todo.entity.Task;

@Component
public class TestRunner implements ApplicationRunner, ExitCodeGenerator, ExitCodeExceptionMapper {
  private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

  private int exitCode;

  @Autowired
  TaskService taskService;
  

  @Override
  public void run(ApplicationArguments args) {
    var tasks = taskService.findAll();
    for (var task : tasks) {
      logger.info("Task: {}", task);
    }
    
    var newTask = new Task();
    newTask.setTitle("hoge");

    newTask.setDescription("fuga");
    
    newTask.setCreated_at("2021-01-01");
    newTask.setUpdated_at("2021-01-01");

  }

  /**
   * Provides exit code for application.
   * <p>
   * If not present this implementation, when normal end always return 0, when abnormal end return exit code that determine exception handling.
   * </p>
   */
  @Override
  public int getExitCode() {
    return exitCode;
  }

  /**
   * Implements processing that map exception to exit code.
   * <p>
   * If not present this implementation, always exit with 1.
   * </p>
   */
  @Override
  public int getExitCode(Throwable exception) {
    return exception.getCause() != null && exception.getCause() instanceof SpelEvaluationException ? 3 : 1;
  }

}