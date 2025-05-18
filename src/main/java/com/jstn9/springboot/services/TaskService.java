package com.jstn9.springboot.services;

import com.jstn9.springboot.models.Task;
import com.jstn9.springboot.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setStatus(updatedTask.getStatus());
                    task.setPriority(updatedTask.getPriority());
                    task.setDueDate(updatedTask.getDueDate());
                    task.setUpdatedAt(updatedTask.getUpdatedAt());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
