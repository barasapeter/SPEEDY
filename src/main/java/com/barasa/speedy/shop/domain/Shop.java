package com.barasa.speedy.shop.domain;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import lombok.*;
import java.util.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop {

    private UUID uuid;
    private String name;
    private String owner;
    private String location;
    private Map<String, Object> addinfo;

    public static Shop fromEntity(ShopEntity e) {
        if (e == null)
            return null;

        return Shop.builder()
                .uuid(e.getUuid())
                .name(e.getName())
                .owner(e.getOwner())
                .location(e.getLocation())
                .addinfo(e.getAddinfo())
                .build();
    }
}
