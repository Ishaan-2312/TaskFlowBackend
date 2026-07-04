package com.EthanHunt.TaskFlow.RequestDTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
}
