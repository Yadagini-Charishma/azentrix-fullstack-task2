package com.azentrix.taskmanagement.repository;

import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByCreatedBy(User user);

    @Query("SELECT b FROM Board b JOIN b.members m WHERE m = :user")
    List<Board> findByMember(@Param("user") User user);

    @Query("SELECT b FROM Board b WHERE b.createdBy = :user OR :user MEMBER OF b.members")
    List<Board> findAllBoardsForUser(@Param("user") User user);
}