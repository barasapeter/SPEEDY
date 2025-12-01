package com.barasa.speedy.shop.infrastructure;

import jakarta.persistence.*;
import lombok.*;

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
    private UUID uuid;

    private String name;
    private String owner;
    private String location;

    @Column(columnDefinition = "jsonb")
    private String addinfo;
}
