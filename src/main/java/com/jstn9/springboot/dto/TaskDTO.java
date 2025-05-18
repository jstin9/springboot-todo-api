package com.jstn9.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jstn9.springboot.enums.Priority;
import com.jstn9.springboot.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Status status;

    private Priority priority;

    @Future(message = "Due date must be in the future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
}
