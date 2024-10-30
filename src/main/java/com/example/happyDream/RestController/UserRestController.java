package com.example.happyDream.RestController;

import com.example.happyDream.DTO.ResponseDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.UserRepository;
import com.example.happyDream.Service.UserService;
import com.example.happyDream.Util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserRestController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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

    //사용자 추가 (회원가입)
    @PostMapping("/users")
    public ResponseDTO userInsert(@RequestBody UserDTO userDTO) {
        userService.userInsert(userDTO.getUsername(), userDTO.getPassword());
        return ResponseDTO.success("v1", HttpServletResponse.SC_OK, "회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseDTO userLogin(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        // 사용자 인증
        if (userService.validateUser(username, password)) {
            Optional<UserEntity> userEntity = userService.findUsername(username);

            Integer userId = userEntity.get().getId();  // userId 추출

            // JWT 발급 (username과 userId 포함)
            String token = jwtUtil.generateToken(username, userId);

            // 클라이언트에 성공 응답과 함께 JWT 토큰 반환
            return ResponseDTO.success("v1", HttpServletResponse.SC_OK, token);
        } else {
            // 인증 실패 시 오류 응답 반환
            return ResponseDTO.error("v1", HttpServletResponse.SC_UNAUTHORIZED, "로그인 실패");
        }
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
