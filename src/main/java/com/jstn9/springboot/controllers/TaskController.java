package com.jstn9.springboot.controllers;

import com.jstn9.springboot.dto.TaskDTO;
import com.jstn9.springboot.enums.Priority;
import com.jstn9.springboot.enums.Status;
import com.jstn9.springboot.models.Task;
import com.jstn9.springboot.repositories.TaskRepository;
import com.jstn9.springboot.services.TaskService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/status/{status}")
    public List<TaskDTO> getTasksByStatus(@PathVariable Status status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/priority/{priority}")
    public List<TaskDTO> getTasksByPriority(@PathVariable Priority priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<TaskDTO> searchTasksByName(@RequestParam String name) {
        return taskRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/due-before")
    public List<TaskDTO> getTasksDueBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return taskRepository.findByDueDateBefore(date).stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(convertToTaskDTO(task));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Task task = convertToTask(taskDTO);

        if (task.getStatus() == null) {
            task.setStatus(Status.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }

        Task createdTask = taskService.createTask(task);
        return convertToTaskDTO(createdTask);
    }

    @PutMapping("/{id}")
    public TaskDTO replaceTask(@PathVariable int id, @Valid @RequestBody TaskDTO taskDTO) {
        Task newTask = convertToTask(taskDTO);
        newTask.setId(id); // важно!
        Task updated = taskService.replaceTask(id, newTask);
        return convertToTaskDTO(updated);
    }

    @PatchMapping("/{id}")
    public TaskDTO updateTask(@PathVariable int id, @Valid @RequestBody TaskDTO taskDTO) {
        Task task = convertToTask(taskDTO);
        Task updatedTask = taskService.updateTask(id, task);
        return convertToTaskDTO(updatedTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO, Task.class);
    }

    private TaskDTO convertToTaskDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }
}