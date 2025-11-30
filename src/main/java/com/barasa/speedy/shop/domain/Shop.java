package com.barasa.speedy.shop.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Shop {

    private final UUID uuid;
    private String name;
    private String location;
    private final UUID ownerUuid;
    private String addinfo;

    public Shop(UUID uuid, String name, String location, UUID ownerUuid, String addinfo) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
        this.ownerUuid = ownerUuid;
        this.addinfo = addinfo;
    }

    public void relocate(String newLocation) {
        if (newLocation == null || newLocation.isBlank()) {
            throw new IllegalArgumentException("New location cannot be empty.");
        }
        this.location = newLocation;
    }
}
