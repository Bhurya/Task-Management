package com.taskmanagement.repository;

import com.taskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.taskId = ?1 and t.isDeleted = false")
    Optional<Task> findByTaskIdAndIsDeletedFalse(Long taskId);

    @Query("select t from Task t where t.isDeleted = false")
    List<Task> findByIsDeletedFalse();


}