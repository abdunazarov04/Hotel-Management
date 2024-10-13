package biz.javachi.HotelManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Integer userId;
    private LocalDateTime createdAt;

    public ConfirmationToken() {}

    public ConfirmationToken(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
