package com.barasa.speedy.bike.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeJpaRepository extends JpaRepository<BikeEntity, String> {
}
