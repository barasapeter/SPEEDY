package com.barasa.speedy.bike.infrastructure;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import com.barasa.speedy.session.infrastructure.SessionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "bike")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeEntity {

    @Id
    private String code;

    private Double rpm;

    @Column(columnDefinition = "TEXT")
    private String addinfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_uuid", nullable = false)
    private ShopEntity shop;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionEntity> sessions;
}
