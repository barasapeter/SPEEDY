package com.barasa.speedy.shop.infrastructure;

import com.barasa.speedy.user.infrastructure.UserEntity;
import com.barasa.speedy.bike.infrastructure.BikeEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID uuid;

    private String name;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String addinfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BikeEntity> inventory;
}
