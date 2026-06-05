package com.azentrix.taskmanagement.websocket;

import com.azentrix.taskmanagement.entity.TaskCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateMessage {
    private String action;  // CREATE, UPDATE, DELETE
    private Long taskId;
    private Long boardId;
    private String title;
    private TaskCard.Status status;
    private TaskCard.Priority priority;
    private String assigneeName;
    private String updatedBy;
}
