package com.EthanHunt.TaskFlow.User;


import com.EthanHunt.TaskFlow.RefreshToken.RefreshToken;
import com.EthanHunt.TaskFlow.RefreshToken.RefreshTokenService;
import com.EthanHunt.TaskFlow.RequestDTO.LoginRequest;
import com.EthanHunt.TaskFlow.RequestDTO.RegisterRequest;
import com.EthanHunt.TaskFlow.ResponseDTO.AuthResponse;
import com.EthanHunt.TaskFlow.Security.JwtService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public @Nullable AuthResponse registerUser(RegisterRequest registerRequest) {
        User user = userRepository.findByEmail(registerRequest.getEmail());
        if(user!=null){
            return AuthResponse.builder().generated(false).message("User Already Existes").build();
        }
        User newUser = User.builder()
                .userName(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(newUser.getEmail())
                        .password(newUser.getPassword())
                        .roles(newUser.getRole().name())
                        .build();
        String jwtToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(newUser);

        return AuthResponse.builder().token(jwtToken).username(newUser.getUserName()).email(newUser.getEmail()).user_id(newUser.getId()).refreshToken(refreshToken.getRefreshToken()).generated(true).build();

    }

    public @Nullable AuthResponse loginUser(LoginRequest loginRequest)throws  Exception {
       Authentication authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getEmail(),loginRequest.getPassword()));
       LOGGER.info("Authentication Object :{}",authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user==null)return AuthResponse.builder().generated(false).message("No User exists with this email").build();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
        LOGGER.info("UserDetails object : {}",userDetails);
        String jwtToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return AuthResponse.builder().token(jwtToken).username(user.getUserName()).email(user.getEmail()).user_id(user.getId()).refreshToken(refreshToken.getRefreshToken()).generated(true).build();


    }
}
