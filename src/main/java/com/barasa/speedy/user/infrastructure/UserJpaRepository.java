package com.barasa.speedy.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    // Basic CRUD methods are inherited.
    // Custom query methods can be added here if needed, but for now,
    // the inherited methods (findById, save, findAll, delete) are sufficient.
}
