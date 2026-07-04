package com.EthanHunt.TaskFlow.RefreshToken;

import com.EthanHunt.TaskFlow.RequestDTO.RefreshTokenRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/refreshToken")
@AllArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        return ResponseEntity.ok(refreshTokenService.generateAccessToken(refreshToken.getRefreshToken()));
    }



}
