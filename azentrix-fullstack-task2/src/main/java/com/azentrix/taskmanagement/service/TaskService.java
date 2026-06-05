package com.azentrix.taskmanagement.service;

import com.azentrix.taskmanagement.dto.TaskDTO;
import com.azentrix.taskmanagement.entity.TaskCard;
import com.azentrix.taskmanagement.entity.User;
import java.util.List;

public interface TaskService {
    TaskCard createTask(TaskDTO dto, User creator);
    TaskCard updateTask(Long id, TaskDTO dto, User currentUser);
    void deleteTask(Long id, User currentUser);
    TaskCard findById(Long id);
    List<TaskCard> findByBoard(Long boardId);
    TaskCard updateStatus(Long id, TaskCard.Status status, User currentUser);
}