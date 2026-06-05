package com.azentrix.taskmanagement.service.impl;

import com.azentrix.taskmanagement.dto.BoardDTO;
import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.User;
import com.azentrix.taskmanagement.repository.BoardRepository;
import com.azentrix.taskmanagement.repository.UserRepository;
import com.azentrix.taskmanagement.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Board createBoard(BoardDTO dto, User creator) {
        List<User> members = new ArrayList<>();
        members.add(creator);

        if (dto.getMemberIds() != null) {
            for (Long memberId : dto.getMemberIds()) {
                userRepository.findById(memberId).ifPresent(members::add);
            }
        }

        Board board = Board.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdBy(creator)
                .members(members)
                .build();

        return boardRepository.save(board);
    }

    @Override
    public Board updateBoard(Long id, BoardDTO dto, User currentUser) {
        Board board = findById(id);
        board.setName(dto.getName());
        board.setDescription(dto.getDescription());
        return boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long id, User currentUser) {
        Board board = findById(id);
        if (!board.getCreatedBy().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != User.Role.ROLE_ADMIN) {
            throw new RuntimeException("Access denied");
        }
        boardRepository.delete(board);
    }

    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    @Override
    public List<Board> findBoardsForUser(User user) {
        return boardRepository.findAllBoardsForUser(user);
    }

    @Override
    public void addMember(Long boardId, Long userId, User currentUser) {
        Board board = findById(boardId);
        User newMember = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!board.getMembers().contains(newMember)) {
            board.getMembers().add(newMember);
            boardRepository.save(board);
        }
    }

    @Override
    public void removeMember(Long boardId, Long userId, User currentUser) {
        Board board = findById(boardId);
        board.getMembers().removeIf(m -> m.getId().equals(userId));
        boardRepository.save(board);
    }
}