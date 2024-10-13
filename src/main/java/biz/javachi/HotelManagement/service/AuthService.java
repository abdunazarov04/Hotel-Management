package biz.javachi.HotelManagement.service;

import biz.javachi.HotelManagement.repository.TokenRepository;
import biz.javachi.HotelManagement.repository.UserRepository;
import biz.javachi.HotelManagement.dto.ApiResponseDto;
import biz.javachi.HotelManagement.dto.RegisterRequest;
import biz.javachi.HotelManagement.entity.ConfirmationToken;
import biz.javachi.HotelManagement.enums.Role;
import biz.javachi.HotelManagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TokenRepository tokenRepository;

    public ApiResponseDto<String> register(RegisterRequest dto) {
        User user = User.builder().build();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setEnabled(false);
        user.setRoles(Set.of(Role.USER));
        userRepository.save(user);
        return sendConfirmationEmail(user);
    }

    private ApiResponseDto<String> sendConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, user.getId());
        tokenRepository.save(confirmationToken);

        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Ro'yxatdan o'tish tasdiqlash");
        message.setText("Tasdiqlash uchun quyidagi havolani bosing: " + confirmationUrl);
        emailSender.send(message);
        return ApiResponseDto.<String>builder().code(200).message("Ro'yxatdan o'tish tasdiqlash uchun email ni tekshiring").data(token).build();
    }

    @Transactional
    public void confirmRegistration(String token) {
        ConfirmationToken confirmationToken = tokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new IllegalArgumentException("Invalid token");
        }

        User user = userRepository.findById(confirmationToken.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.deleteByToken(token);
    }
}