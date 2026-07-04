package com.EthanHunt.TaskFlow.User;

import com.EthanHunt.TaskFlow.RequestDTO.LoginRequest;
import com.EthanHunt.TaskFlow.RequestDTO.RegisterRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest)throws  Exception{
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest)throws  Exception{
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER')")
    public String userHello() {
        return "Hello User";
    }


}
