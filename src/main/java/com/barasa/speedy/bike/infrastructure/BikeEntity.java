package com.barasa.speedy.bike.infrastructure;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import com.barasa.speedy.session.infrastructure.SessionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_uuid", nullable = false)
    private ShopEntity shop;

    private Integer rpm;

    @Column(columnDefinition = "jsonb")
    private String addinfo;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    private List<SessionEntity> sessions = new ArrayList<>();
}
