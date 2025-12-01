package com.barasa.speedy.user.domain;

import com.barasa.speedy.user.infrastructure.UserEntity;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private UUID uuid;
    private String name;
    private String phone;
    private String addinfo; // JSONB text or other metadata

    public static User fromEntity(UserEntity e) {
        if (e == null)
            return null;

        return User.builder()
                .uuid(e.getUuid())
                .name(e.getName())
                .phone(e.getPhone())
                .addinfo(e.getAddinfo())
                .build();
    }
}
