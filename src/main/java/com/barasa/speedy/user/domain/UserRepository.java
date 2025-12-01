package com.barasa.speedy.user.domain;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();
}
