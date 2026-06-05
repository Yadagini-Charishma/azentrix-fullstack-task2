package com.azentrix.taskmanagement.controller;

import com.azentrix.taskmanagement.entity.Board;
import com.azentrix.taskmanagement.entity.User;
import com.azentrix.taskmanagement.service.BoardService;
import com.azentrix.taskmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        List<Board> boards = boardService.findBoardsForUser(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("boards", boards);
        model.addAttribute("boardCount", boards.size());

        return "dashboard";
    }
}