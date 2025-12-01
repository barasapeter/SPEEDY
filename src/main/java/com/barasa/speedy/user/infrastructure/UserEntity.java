package com.barasa.speedy.user.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

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
    private String addinfo;
}
