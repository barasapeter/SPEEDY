package com.barasa.speedy.user.infrastructure;

import com.barasa.speedy.common.util.JsonMapConverter;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    private UUID uuid;

    private String name;
    private String phone;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> addinfo;
}
