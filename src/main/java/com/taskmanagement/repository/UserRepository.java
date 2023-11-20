package com.taskmanagement.repository;

import com.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.userId = ?1 and u.isDeleted = false")
    Optional<User> findByUserIdAndIsDeletedFalse(Long userId);

    @Query("select u from User u where u.isDeleted = false")
    List<User> findByIsDeletedFalse();

    @Query("select u from User u where u.isDeleted = false and u.email= ?1")
    Optional<User> findByUserName(String username);


}