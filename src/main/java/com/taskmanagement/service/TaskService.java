package com.taskmanagement.service;

import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.model.Task;

import java.util.List;

public interface TaskService {

    Task createNewTask(TaskDto task);

    Task updateTask(TaskDto task);

    Task getTaskById(Long taskId);

    List<Task> findAllTasks();

    String deleteTaskById(Long taskId);

}
