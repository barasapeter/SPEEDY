package com.barasa.speedy.bike.infrastructure;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bike")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeEntity {

    @Id
    private String code;

    private Double rpm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_uuid")
    private ShopEntity shop;

    @Column(columnDefinition = "jsonb")
    private String addinfo;
}
