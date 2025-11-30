package com.barasa.speedy.user.domain;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByUuid(UUID uuid);

    User save(User user);

    void delete(User user);

    List<User> findAll();
}
