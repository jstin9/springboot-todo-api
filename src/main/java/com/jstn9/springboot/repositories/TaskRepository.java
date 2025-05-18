package com.jstn9.springboot.repositories;

import com.jstn9.springboot.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
