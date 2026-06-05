package com.azentrix.taskmanagement.dto;

import com.azentrix.taskmanagement.entity.TaskCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private TaskCard.Status status;

    @NotNull(message = "Priority is required")
    private TaskCard.Priority priority;

    @NotNull(message = "Board ID is required")
    private Long boardId;

    private Long assigneeId;

    private LocalDate dueDate;
}