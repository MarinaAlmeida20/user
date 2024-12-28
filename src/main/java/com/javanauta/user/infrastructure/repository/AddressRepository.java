package com.javanauta.user.infrastructure.repository;

import com.javanauta.user.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
