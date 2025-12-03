package com.barasa.speedy.shop.domain;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import com.barasa.speedy.shop.infrastructure.ShopJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShopService implements ShopRepository {

    private final ShopJpaRepository jpa;

    @Override
    public Shop save(Shop shop) {
        ShopEntity entity = ShopEntity.builder()
                .uuid(shop.getUuid())
                .name(shop.getName())
                .owner(shop.getOwner())
                .location(shop.getLocation())
                .addinfo(shop.getAddinfo())
                .build();

        ShopEntity saved = jpa.save(entity);
        return Shop.fromEntity(saved);
    }

    @Override
    public Optional<Shop> findById(UUID id) {
        return jpa.findById(id).map(Shop::fromEntity);
    }

    @Override
    public Optional<Shop> findByOwner(String owner) {
        return jpa.findByOwner(owner).map(Shop::fromEntity);
    }

    @Override
    public List<Shop> findAll() {
        return jpa.findAll().stream().map(Shop::fromEntity).toList();
    }
}
