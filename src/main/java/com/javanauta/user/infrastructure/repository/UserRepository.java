package com.javanauta.user.infrastructure.repository;

import com.javanauta.user.infrastructure.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(@Param("email") String email);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}
