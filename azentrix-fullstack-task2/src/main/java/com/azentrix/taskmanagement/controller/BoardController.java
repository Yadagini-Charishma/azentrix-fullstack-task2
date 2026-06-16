package com.azentrix.taskmanagement.controller;

import com.azentrix.taskmanagement.dto.BoardDTO;
import com.azentrix.taskmanagement.dto.TaskDTO;
import com.azentrix.taskmanagement.entity.*;
import com.azentrix.taskmanagement.service.BoardService;
import com.azentrix.taskmanagement.service.TaskService;
import com.azentrix.taskmanagement.service.UserService;
import com.azentrix.taskmanagement.websocket.TaskUpdateMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/new")
    public String newBoardForm(Model model) {
        model.addAttribute("boardDTO", new BoardDTO());
        model.addAttribute("allUsers", userService.findAllUsers());
        return "board-form";
    }

    @PostMapping("/new")
    public String createBoard(@Valid @ModelAttribute BoardDTO dto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User creator = userService.findByUsername(userDetails.getUsername());
        Board board = boardService.createBoard(dto, creator);
        return "redirect:/boards/" + board.getId();
    }

    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Board board = boardService.findById(id);
        User currentUser = userService.findByUsername(userDetails.getUsername());
        List<TaskCard> allTasks = taskService.findByBoard(id);

        List<TaskCard> todoTasks = allTasks.stream()
                .filter(t -> t.getStatus() == TaskCard.Status.TODO)
                .collect(Collectors.toList());
        List<TaskCard> inProgressTasks = allTasks.stream()
                .filter(t -> t.getStatus() == TaskCard.Status.IN_PROGRESS)
                .collect(Collectors.toList());
        List<TaskCard> doneTasks = allTasks.stream()
                .filter(t -> t.getStatus() == TaskCard.Status.DONE)
                .collect(Collectors.toList());

        model.addAttribute("board", board);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("todoTasks", todoTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("doneTasks", doneTasks);
        model.addAttribute("taskDTO", new TaskDTO());
        model.addAttribute("allUsers", board.getMembers());
        model.addAttribute("isAdmin", currentUser.getRole() == User.Role.ROLE_ADMIN);

        return "board";
    }

    @PostMapping("/{boardId}/tasks/new")
    public String createTask(@PathVariable Long boardId,
                             @Valid @ModelAttribute TaskDTO dto,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User creator = userService.findByUsername(userDetails.getUsername());
        dto.setBoardId(boardId);
        TaskCard task = taskService.createTask(dto, creator);

        TaskUpdateMessage msg = new TaskUpdateMessage(
                "CREATE", task.getId(), boardId,
                task.getTitle(), task.getStatus(), task.getPriority(),
                task.getAssignee() != null ? task.getAssignee().getUsername() : null,
                creator.getUsername());
        messagingTemplate.convertAndSend("/topic/board/" + boardId, msg);

        return "redirect:/boards/" + boardId;
    }

    @PostMapping("/{boardId}/tasks/{taskId}/status")
    @ResponseBody
    public String updateTaskStatus(@PathVariable Long boardId,
                                   @PathVariable Long taskId,
                                   @RequestParam String status,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        TaskCard.Status newStatus = TaskCard.Status.valueOf(status);
        TaskCard task = taskService.updateStatus(taskId, newStatus, user);

        TaskUpdateMessage msg = new TaskUpdateMessage(
                "UPDATE", task.getId(), boardId,
                task.getTitle(), task.getStatus(), task.getPriority(),
                task.getAssignee() != null ? task.getAssignee().getUsername() : null,
                user.getUsername());
        messagingTemplate.convertAndSend("/topic/board/" + boardId, msg);

        return "ok";
    }
    @GetMapping("/{id}/edit")
    public String editBoardForm(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        Board board = boardService.findById(id);
        User currentUser = userService.findByUsername(userDetails.getUsername());

        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setName(board.getName());
        dto.setDescription(board.getDescription());

        model.addAttribute("boardDTO", dto);
        model.addAttribute("board", board);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("currentMembers", board.getMembers());

        return "board-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBoard(@PathVariable Long id,
                              @Valid @ModelAttribute BoardDTO dto,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        boardService.updateBoard(id, dto, user);
        return "redirect:/boards/" + id;
    }

    @PostMapping("/{boardId}/members/add")
    public String addMember(@PathVariable Long boardId,
                            @RequestParam Long userId,
                            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        boardService.addMember(boardId, userId, currentUser);
        return "redirect:/boards/" + boardId + "/edit";
    }

    @PostMapping("/{boardId}/members/remove")
    public String removeMember(@PathVariable Long boardId,
                               @RequestParam Long userId,
                               @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        boardService.removeMember(boardId, userId, currentUser);
        return "redirect:/boards/" + boardId + "/edit";
    }
    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        boardService.deleteBoard(id, user);
        return "redirect:/dashboard";
    }
    @PostMapping("/{boardId}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable Long boardId,
                             @PathVariable Long taskId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        taskService.deleteTask(taskId, user);

        TaskUpdateMessage msg = new TaskUpdateMessage(
                "DELETE", taskId, boardId,
                null, null, null, null, user.getUsername());
        messagingTemplate.convertAndSend("/topic/board/" + boardId, msg);

        return "redirect:/boards/" + boardId;
    }

    @GetMapping("/{boardId}/tasks/{taskId}/edit")
    public String editTaskForm(@PathVariable Long boardId,
                               @PathVariable Long taskId,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        TaskCard task = taskService.findById(taskId);
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Board board = boardService.findById(boardId);

        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setBoardId(boardId);
        if (task.getAssignee() != null) {
            dto.setAssigneeId(task.getAssignee().getId());
        }

        model.addAttribute("taskDTO", dto);
        model.addAttribute("board", board);
        model.addAttribute("allUsers", board.getMembers());
        model.addAttribute("currentUser", currentUser);

        return "task-form";
    }

    @PostMapping("/{boardId}/tasks/{taskId}/edit")
    public String updateTask(@PathVariable Long boardId,
                             @PathVariable Long taskId,
                             @Valid @ModelAttribute TaskDTO dto,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        dto.setBoardId(boardId);
        TaskCard task = taskService.updateTask(taskId, dto, user);

        TaskUpdateMessage msg = new TaskUpdateMessage(
                "UPDATE", task.getId(), boardId,
                task.getTitle(), task.getStatus(), task.getPriority(),
                task.getAssignee() != null ? task.getAssignee().getUsername() : null,
                user.getUsername());
        messagingTemplate.convertAndSend("/topic/board/" + boardId, msg);

        return "redirect:/boards/" + boardId;
    }
}