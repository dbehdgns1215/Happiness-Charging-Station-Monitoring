package com.example.happyDream.Service;

import com.example.happyDream.DTO.ChargerDTO;
import com.example.happyDream.DTO.UserDTO;
import com.example.happyDream.Entity.ChargerEntity;
import com.example.happyDream.Entity.UserEntity;
import com.example.happyDream.Repository.UserRepository;
import com.example.happyDream.Util.Converter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> userSelectAll() {
        List<UserEntity> entityList = this.userRepository.findAll();
        return Converter.EntityListToDtoList(entityList, UserEntity::toDTO);
    }

    public void userDeleteAll() {
        this.userRepository.deleteAll();
    }

    public void userInsert(String username, String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        this.userRepository.save(userDTO.toEntity());
    }

    public UserDTO userSelect(Integer id) {
        Optional<UserEntity> entity = this.userRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException();
        }
        return entity.get().toDTO();

    }

    public void userDelete(Integer id) {
        this.userRepository.deleteById(id);
    }
}
