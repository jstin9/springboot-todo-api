package com.jstn9.springboot.controllers;

import com.jstn9.springboot.dto.TaskDTO;
import com.jstn9.springboot.enums.Priority;
import com.jstn9.springboot.enums.Status;
import com.jstn9.springboot.models.Task;
import com.jstn9.springboot.services.TaskService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int id) {
        Optional<Task> optionalTask = taskService.getTaskById(id);
        return optionalTask
                .map(task -> ResponseEntity.ok(convertToTaskDTO(task)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = convertToTask(taskDTO);
        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(convertToTaskDTO(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable int id, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Task updatedtask = taskService.updateTask(id, convertToTask(taskDTO));
            return ResponseEntity.ok(convertToTaskDTO(updatedtask));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO convertToTaskDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}
