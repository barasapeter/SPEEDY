package com.barasa.speedy.bike.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import com.barasa.speedy.bike.infrastructure.BikeJpaRepository;
import com.barasa.speedy.shop.infrastructure.ShopEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BikeService implements BikeRepository {

    private final BikeJpaRepository jpa;
    private final EntityManager em;

    @Override
    public Bike save(Bike bike) {

        ShopEntity shopRef = null;
        if (bike.getShopUuid() != null) {
            shopRef = em.getReference(ShopEntity.class, bike.getShopUuid());
        }

        BikeEntity entity = BikeEntity.builder()
                .code(bike.getCode())
                .rpm(bike.getRpm())
                .addinfo(bike.getAddinfo())
                .shop(shopRef)
                .build();

        jpa.save(entity);

        return Bike.fromEntity(entity);
    }

    @Override
    public List<Bike> findAll() {
        return jpa.findAll()
                .stream()
                .map(Bike::fromEntity)
                .toList();
    }

    @Override
    public Optional<Bike> findByCode(String code) {
        return jpa.findById(code)
                .map(Bike::fromEntity);
    }

    @Override
    public List<Bike> findByShopUuid(UUID uuid) {
        return jpa.findByShopUuid(uuid)
                .stream()
                .map(Bike::fromEntity)
                .toList();
    }

    @Override
    public void delete(Bike bike) {
        jpa.deleteById(bike.getCode());
    }
}
