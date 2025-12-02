package com.barasa.speedy.shop.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import java.util.UUID;

import com.barasa.speedy.common.util.JsonMapConverter;

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
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> addinfo;
}
