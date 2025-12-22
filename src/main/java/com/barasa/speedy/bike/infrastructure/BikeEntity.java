package com.barasa.speedy.bike.infrastructure;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> addinfo;
}
