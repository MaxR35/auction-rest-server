package com.rougeux.projet.auction.controller.rest;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.dto.user.UserDto;
import com.rougeux.projet.auction.mapper.HttpMapper;
import com.rougeux.projet.auction.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/api/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/api/users/{slug}")
    public ResponseEntity<ApiResponse<Object>> getUserBySlug(@PathVariable String slug) {
        ApiResponse<Object> response = userService.getUser(slug);

        return ResponseEntity.status(HttpMapper.toHttpStatus(response.code())).body(response);
    }
}
