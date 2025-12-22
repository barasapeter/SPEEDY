package com.barasa.speedy.user.domain;

import com.barasa.speedy.user.infrastructure.UserEntity;
import lombok.*;
import java.util.*;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID uuid;
    private String name;
    private String phone;
    private Map<String, Object> addinfo;

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

    public String getID() {
        return this.uuid.toString();
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return (String) addinfo.get("email");
    }
}
