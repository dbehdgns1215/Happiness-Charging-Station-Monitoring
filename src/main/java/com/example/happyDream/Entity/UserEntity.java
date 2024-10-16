package com.example.happyDream.Entity;

import com.example.happyDream.DTO.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user") //MYSQL 외 타 DB는 문제 발생할 수 있음(users, member 등 대응)
@Getter //Setter 미사용
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자 외부 접근 차단
@EntityListeners(AuditingEntityListener.class) //Auditing 사용 명시
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //유저 식별자

    @NotNull
    @Column(length = 32)
    private String username; //아이디

    @NotNull
    @Column(length = 32)
    private String password; //비밀번호

    @NotNull
    @Column(length = 64, unique = true)
    private String email; //이메일

    //TODO - 추후 Enum 전환하고, 컨버터 추가
    @NotNull
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Byte userType; //유저 유형

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; //데이터 생성일

    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt; //데이터 수정일

    @NotNull
    @Column(columnDefinition = "TINYINT(1) UNSIGNED DEFAULT 0")
    private Boolean deletedYn; //데이터 삭제 여부

    @Column(insertable = false)
    private LocalDateTime deletedAt; //데이터 삭제일

    @Builder
    public UserEntity(Integer id, String username, String password, String email, Byte userType, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean deletedYn, LocalDateTime deletedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedYn = deletedYn;
        this.deletedAt = deletedAt;
    }

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .userType(userType)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .deletedYn(deletedYn)
                .deletedAt(deletedAt)
                .build();
    }
}
