package com.barasa.speedy.shop.infrastructure;

import com.barasa.speedy.bike.infrastructure.BikeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID uuid;

    private String name;
    private String owner;
    private String location;

    @Column(columnDefinition = "jsonb")
    private String addinfo;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<BikeEntity> bikes = new ArrayList<>();
}
