package com.example.happyDream.Controller;

import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Service.UserService;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String reviewSelectAll(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "userManagement";
    }
}
