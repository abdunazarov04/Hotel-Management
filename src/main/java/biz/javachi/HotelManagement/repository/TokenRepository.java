package biz.javachi.HotelManagement.repository;

import biz.javachi.HotelManagement.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String token);
    void deleteByToken(String token); // Tokenni o'chirish uchun metod
}
