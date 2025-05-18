package com.jstn9.springboot.services;

import com.jstn9.springboot.exceptions.TaskNotFoundException;
import com.jstn9.springboot.models.Task;
import com.jstn9.springboot.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        if (updatedTask.getName() != null) {
            existingTask.setName(updatedTask.getName());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        if (updatedTask.getPriority() != null) {
            existingTask.setPriority(updatedTask.getPriority());
        }

        if (updatedTask.getDueDate() != null) {
            existingTask.setDueDate(updatedTask.getDueDate());
        }

        return taskRepository.save(existingTask);
    }

    public Task replaceTask(Integer id, Task newTask) {
        Task existing = getTaskById(id);
        newTask.setCreatedAt(existing.getCreatedAt());
        return taskRepository.save(newTask);
    }

    public void deleteTask(Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}