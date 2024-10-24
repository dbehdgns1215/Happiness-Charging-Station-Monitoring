package com.example.happyDream.Service;

import com.example.happyDream.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserReviewServiceFacade {
    private final UserService userService;
    private final ReviewService reviewService;

    public UserReviewServiceFacade(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    /* ===== UserService ===== */
    public List<UserDTO> userSelectAll(){
        return this.userService.userSelectAll();
    }

    public void userDeleteAll(){
        this.userService.userDeleteAll();
    }

    public void userInsert(String username,
                           String password,
                           String email,
                           Byte userType,
                           Boolean deletedYn){
        this.userService.userInsert(username, password, email, userType, deletedYn);
    }

    public UserDTO userSelect(Integer id){
        return this.userService.userSelect(id);
    }

    public void userDelete(Integer id){
        this.userService.userDelete(id);
    }

    /* ===== ReviewService ===== */

}
