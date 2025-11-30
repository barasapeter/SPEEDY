package com.barasa.speedy.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUuid(UUID uuid);

}
