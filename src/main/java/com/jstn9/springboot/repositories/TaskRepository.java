package com.jstn9.springboot.repositories;

import com.jstn9.springboot.enums.Priority;
import com.jstn9.springboot.enums.Status;
import com.jstn9.springboot.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByStatus(Status status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByDueDateBefore(LocalDate date);

    List<Task> findByNameContainingIgnoreCase(String name);

}