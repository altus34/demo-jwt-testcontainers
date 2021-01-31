package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/me")
    public Map<String, String> user() {
        return Map.of("key-1", "value-1");
    }

    @GetMapping("/me2")
    public Map<String, String> user2(@AuthenticationPrincipal Jwt principal) {
        return Map.of("key-1", "value-1");
    }
}
