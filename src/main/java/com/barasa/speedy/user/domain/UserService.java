package com.barasa.speedy.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service; // <-- ADD THIS
import java.util.UUID;

@Service // <-- AND THIS
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getExistingUser(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("User not found with UUID: " + uuid));
    }

    public User createNewUser(String name, String phone) {
        UUID newUuid = UUID.randomUUID();
        User newUser = new User(newUuid, name, phone, null);
        return userRepository.save(newUser);
    }
}
