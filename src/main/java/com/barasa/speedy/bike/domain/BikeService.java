package com.barasa.speedy.bike.domain;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import com.barasa.speedy.bike.infrastructure.BikeJpaRepository;
import com.barasa.speedy.bike.domain.BikeRepository;
import com.barasa.speedy.shop.infrastructure.ShopEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BikeService implements BikeRepository {

    private final BikeJpaRepository jpa;

    @Override
    public Bike save(Bike bike) {
        BikeEntity entity = BikeEntity.builder()
                .code(bike.getCode())
                .rpm(bike.getRpm())
                .addinfo(bike.getAddinfo())
                .shop(ShopEntity.builder().uuid(bike.getShopUuid()).build())
                .build();

        jpa.save(entity);
        return bike;
    }

    @Override
    public List<Bike> findAll() {
        return jpa.findAll().stream().map(Bike::fromEntity).toList();
    }

    @Override
    public Optional<Bike> findByCode(String code) {
        return jpa.findById(code).map(Bike::fromEntity);
    }
}
