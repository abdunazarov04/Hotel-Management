package biz.javachi.HotelManagement.controller;

import biz.javachi.HotelManagement.dto.ApiResponseDto;
import biz.javachi.HotelManagement.dto.LoginRequest;
import biz.javachi.HotelManagement.dto.RegisterRequest;
import biz.javachi.HotelManagement.service.AuthService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

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
    @GetMapping("/user")
//    @PreAuthorize("hasAnyAuthority('USER')")
    public String userApi() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        System.out.println("User authenticated: " + authentication.getName());
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Credentials: " + authentication.getCredentials());
        System.out.println("Details: " + authentication.getDetails());

        /*if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "User is not authenticated";
        }*/

        String username = authentication.getName();
        return "Bu API faqat foydalanuvchilar uchun. Joriy foydalanuvchi: " + username;
    }
}

