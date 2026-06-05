package com.azentrix.taskmanagement.repository;

import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.TaskCard;
import com.azentrix.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskCardRepository extends JpaRepository<TaskCard, Long> {
    List<TaskCard> findByBoard(Board board);
    List<TaskCard> findByBoardAndStatus(Board board, TaskCard.Status status);
    List<TaskCard> findByAssignee(User user);
    List<TaskCard> findByBoardOrderByCreatedAtDesc(Board board);
}