package com.example.happyDream.Controller;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Service.UserReviewServiceFacade;
import com.example.happyDream.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserReviewServiceFacade userReviewServiceFacade;

    @Autowired
    public UserController(UserReviewServiceFacade userReviewServiceFacade) {
        this.userReviewServiceFacade = userReviewServiceFacade;
    }

    //전체 사용자 조회
    @GetMapping("/users")
    public String userSelectAll(Model model) {
        List<UserDTO> users = this.userReviewServiceFacade.userSelectAll();
        return " ";
    }

    //전체 사용자 삭제
    @DeleteMapping("/users")
    public String userDeleteAll() {
        this.userReviewServiceFacade.userDeleteAll();
        return " ";
    }

    //사용자 추가
    @PostMapping("/users")
    public String userInsert(@RequestParam(value="username") String username,
                             @RequestParam(value="password") String password,
                             @RequestParam(value="email") String email,
                             @RequestParam(value="user_type") Byte userType,
                             @RequestParam(value="deleted_yn") Boolean deletedYn) {
        this.userReviewServiceFacade.userInsert(username, password, email, userType, deletedYn);
        return " ";
    }

    //특정 사용자 조회
    @GetMapping("/users/{id}")
    public String userSelect(@PathVariable("id") Integer id) {
        UserDTO user = this.userReviewServiceFacade.userSelect(id);
        return " ";
    }

    //특정 사용자 삭제
    @DeleteMapping("/users/{id}")
    public String userDelete(@PathVariable("id") Integer id) {
        this.userReviewServiceFacade.userDelete(id);
        return " ";
    }
}
