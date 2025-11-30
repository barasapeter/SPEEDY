package com.barasa.speedy.shop.domain;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop getShopById(UUID shopUuid) {
        return shopRepository.findByUuid(shopUuid)
                .orElseThrow(() -> new RuntimeException("Shop not found with UUID: " + shopUuid));
    }

    public Shop registerNewShop(String name, String location, UUID ownerUuid) {
        UUID newUuid = UUID.randomUUID();
        Shop newShop = new Shop(newUuid, name, location, ownerUuid, null);
        return shopRepository.save(newShop);
    }
}
