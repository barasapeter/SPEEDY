package com.barasa.speedy.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhone(String phone);

    @Query(value = "SELECT * FROM users u WHERE u.addinfo->>'email' = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(@Param("email") String email);
}
