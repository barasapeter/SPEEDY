package com.barasa.speedy.user.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class User {

    private final UUID uuid;
    private String name;
    private String phone;
    private String addinfo;

    public User(UUID uuid, String name, String phone, String addinfo) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.addinfo = addinfo;
    }

    public void updateContactInfo(String newName, String newPhone) {
        if (newName != null && !newName.isBlank()) {
            this.name = newName;
        }
        if (newPhone != null && !newPhone.isBlank()) {
            this.phone = newPhone;
        }
    }
}
