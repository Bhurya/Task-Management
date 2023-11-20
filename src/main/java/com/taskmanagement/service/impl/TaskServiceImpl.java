package com.taskmanagement.service.impl;

import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.exception.GenericExceptions;
import com.taskmanagement.model.Task;
import com.taskmanagement.model.User;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import com.taskmanagement.service.TaskService;
import com.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Task createNewTask(TaskDto task) {

        User user = userService.findUserById(task.getUserId());

        Task taskObject = Task.builder()
                .taskName(task.getTaskName())
                .assignTaskData(new Date())
                .taskDescription(task.getTaskDescription())
                .dueDate(new Date(task.getDueDate()))
                .user(user)
                .build();
        taskRepository.save(taskObject);
        return taskObject;

    }

    @CacheEvict(value = "task", key = "#task.taskId")
    @Override
    public Task updateTask(TaskDto task) {
        Task existingTask = taskRepository.findById(task.getTaskId()).orElseThrow(() -> new GenericExceptions("Task Not found !"));
        User user = userService.findUserById(task.getUserId());

        existingTask.setTaskName(task.getTaskName());
        existingTask.setAssignTaskData(existingTask.getAssignTaskData());
        existingTask.setTaskDescription(task.getTaskDescription());
        existingTask.setDueDate(new Date(task.getDueDate()));
        existingTask.setUser(user);
        taskRepository.save(existingTask);
        return existingTask;
    }

    @Cacheable("task")
    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findByTaskIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new GenericExceptions("Task not found"));
    }

    @Override
    public List<Task> findAllTasks() {
        return  taskRepository.findByIsDeletedFalse();
//        return taskList.stream().map(e -> TaskDto.builder()
//                .taskId(e.getTaskId())
//                .taskName(e.getTaskName())
//                .dueDate(e.getDueDate().getTime())
//                .userId(e.getUser().getUserId())
//                .taskDescription(e.getTaskDescription())
//                .build())
//                .collect(Collectors.toList());
    }

    @CacheEvict(value = "task", key = "#taskId")
    @Override
    public String deleteTaskById(Long taskId) {
        return taskRepository.findByTaskIdAndIsDeletedFalse(taskId)
                .map(task -> {
                    task.setDeleted(true);
                    taskRepository.save(task);
                    return "Task deleted Successfully";
                }).orElse("Task not found");
    }
}
