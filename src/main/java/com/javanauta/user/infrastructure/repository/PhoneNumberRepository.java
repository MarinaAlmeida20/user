package com.javanauta.user.infrastructure.repository;

import com.javanauta.user.infrastructure.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
}
