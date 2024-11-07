package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 전체 사용자 조회
    @GetMapping("/users")
    public ResponseDTO userSelectAll() {
        List<UserDTO> users = this.userService.userSelectAll();
        return ResponseDTO.success("V1", HttpServletResponse.SC_OK, users);
    }

    //전체 사용자 삭제
    @DeleteMapping("/users")
    public ResponseDTO userDeleteAll() {
        this.userService.userDeleteAll();
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    //사용자 추가
    @PostMapping("/users")
    public ResponseDTO userInsert(@RequestParam(value="username") String username,
                             @RequestParam(value="password") String password,
                             @RequestParam(value="email") String email,
                             @RequestParam(value="user_type") Byte userType) {
        this.userService.userInsert(username, password, email, userType);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }

    //특정 사용자 조회
    @GetMapping("/users/{id}")
    public ResponseDTO userSelect(@PathVariable("id") Integer id) {
        UserDTO user = this.userService.userSelect(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, Collections.singletonList(user));
    }

    //특정 사용자 삭제
    @DeleteMapping("/users/{id}")
    public ResponseDTO userDelete(@PathVariable("id") Integer id) {
        this.userService.userDelete(id);
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK);
    }
}
