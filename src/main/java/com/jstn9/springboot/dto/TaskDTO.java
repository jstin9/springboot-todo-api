package com.jstn9.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jstn9.springboot.enums.Priority;
import com.jstn9.springboot.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class TaskDTO {

    private int id;
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Status status;

    private Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    public TaskDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
