package biz.javachi.HotelManagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping("/check")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String adminApi() {
        return "This API is for admins";
    }
}
