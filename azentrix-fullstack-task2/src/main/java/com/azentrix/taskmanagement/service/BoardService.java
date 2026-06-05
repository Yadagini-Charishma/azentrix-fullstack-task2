package com.azentrix.taskmanagement.service;

import com.azentrix.taskmanagement.dto.BoardDTO;
import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.User;
import java.util.List;

public interface BoardService {
    Board createBoard(BoardDTO dto, User creator);
    Board updateBoard(Long id, BoardDTO dto, User currentUser);
    void deleteBoard(Long id, User currentUser);
    Board findById(Long id);
    List<Board> findBoardsForUser(User user);
    void addMember(Long boardId, Long userId, User currentUser);
    void removeMember(Long boardId, Long userId, User currentUser);
}