package com.EthanHunt.TaskFlow.ResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String refreshToken;
    private boolean generated;
    private String message;
    private String username;
    private Long user_id;
    private String email;

}
