package com.taskmanagement.controller;

import com.taskmanagement.dto.TaskDto;
import com.taskmanagement.model.Task;
import com.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task/")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "createNewTask endpoint", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("createNewTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto){
        Task task = taskService.createNewTask(taskDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(task);
    }

    @Operation(summary = "updateTask endpoint", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("updateTask")
    public ResponseEntity<Task> updateTask(@RequestBody TaskDto taskDto){
        Task task = taskService.updateTask(taskDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }

    @Operation(summary = "getTaskById endpoint", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("getTaskById")
    public ResponseEntity<Task> getTaskById(Long taskId){
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }

    @Operation(summary = "deleteTaskById endpoint", security = @SecurityRequirement(name = "Authorization"))
    @DeleteMapping("deleteTaskById")
    public ResponseEntity<String> deleteTaskById(Long taskId){
        String task = taskService.deleteTaskById(taskId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }

    @Operation(summary = "findListOfTasks endpoint", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("findListOfTasks")
    public ResponseEntity<List<Task>> findListOfTasks(){
        List<Task> tasks = taskService.findAllTasks();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tasks);
    }
}
