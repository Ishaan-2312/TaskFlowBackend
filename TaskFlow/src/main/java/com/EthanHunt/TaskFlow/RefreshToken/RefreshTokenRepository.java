package com.EthanHunt.TaskFlow.RefreshToken;

import com.EthanHunt.TaskFlow.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUser(User user);

    RefreshToken findByRefreshToken(String refreshToken);
}
