package biz.javachi.HotelManagement.controller;

import biz.javachi.HotelManagement.dto.ApiResponseDto;
import biz.javachi.HotelManagement.dto.LoginRequest;
import biz.javachi.HotelManagement.dto.RegisterRequest;
import biz.javachi.HotelManagement.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ApiResponseDto<String> register(@RequestBody RegisterRequest dto) {
        return authService.register(dto);
    }

    @GetMapping("/confirm")
    @Hidden
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        authService.confirmRegistration(token);
        return ResponseEntity.ok("Ro'yxatdan o'tish muvaffaqiyatli yakunlandi!");
    }

    @PostMapping("/login")
    public ApiResponseDto<String> login(@RequestBody LoginRequest dto) {
        return this.authService.login(dto);
    }
}

