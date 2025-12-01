package com.barasa.speedy.user.domain;

import com.barasa.speedy.user.infrastructure.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserRepository {

    private final UserJpaRepository jpa;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .phone(user.getPhone())
                .addinfo(user.getAddinfo())
                .build();

        jpa.save(entity);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpa.findById(id).map(User::fromEntity);
    }

    @Override
    public List<User> findAll() {
        return jpa.findAll().stream().map(User::fromEntity).toList();
    }
}
