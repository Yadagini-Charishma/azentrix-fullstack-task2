package com.azentrix.taskmanagement.service.impl;

import com.azentrix.taskmanagement.dto.TaskDTO;
import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.TaskCard;
import com.azentrix.taskmanagement.entity.User;
import com.azentrix.taskmanagement.repository.BoardRepository;
import com.azentrix.taskmanagement.repository.TaskCardRepository;
import com.azentrix.taskmanagement.repository.UserRepository;
import com.azentrix.taskmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskCardRepository taskCardRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskCard createTask(TaskDTO dto, User creator) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Board not found"));

        TaskCard task = TaskCard.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : TaskCard.Status.TODO)
                .priority(dto.getPriority() != null ? dto.getPriority() : TaskCard.Priority.MEDIUM)
                .board(board)
                .createdBy(creator)
                .dueDate(dto.getDueDate())
                .build();

        if (dto.getAssigneeId() != null) {
            userRepository.findById(dto.getAssigneeId()).ifPresent(task::setAssignee);
        }

        return taskCardRepository.save(task);
    }

    @Override
    public TaskCard updateTask(Long id, TaskDTO dto, User currentUser) {
        TaskCard task = findById(id);

        if (!task.getCreatedBy().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != User.Role.ROLE_ADMIN) {
            throw new RuntimeException("Access denied: You can only edit your own tasks");
        }

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());

        if (dto.getAssigneeId() != null) {
            userRepository.findById(dto.getAssigneeId()).ifPresent(task::setAssignee);
        } else {
            task.setAssignee(null);
        }

        return taskCardRepository.save(task);
    }

    @Override
    public void deleteTask(Long id, User currentUser) {
        TaskCard task = findById(id);

        if (!task.getCreatedBy().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != User.Role.ROLE_ADMIN) {
            throw new RuntimeException("Access denied");
        }

        taskCardRepository.delete(task);
    }

    @Override
    public TaskCard findById(Long id) {
        return taskCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public List<TaskCard> findByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        return taskCardRepository.findByBoardOrderByCreatedAtDesc(board);
    }

    @Override
    public TaskCard updateStatus(Long id, TaskCard.Status status, User currentUser) {
        TaskCard task = findById(id);
        task.setStatus(status);
        return taskCardRepository.save(task);
    }
}