package com.azentrix.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class BoardDTO {

    private Long id;

    @NotBlank(message = "Board name is required")
    private String name;

    private String description;

    private List<Long> memberIds;
}