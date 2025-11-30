package com.barasa.speedy.user.infrastructure;

import com.barasa.speedy.shop.infrastructure.ShopEntity;
import com.barasa.speedy.session.infrastructure.SessionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID uuid;

    private String name;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String addinfo;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopEntity> ownedShops;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessionEntity> sessions;
}
