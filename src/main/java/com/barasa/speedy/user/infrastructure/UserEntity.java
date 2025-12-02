package com.barasa.speedy.user.infrastructure;

import com.barasa.speedy.common.util.JsonMapConverter;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> addinfo;

}
