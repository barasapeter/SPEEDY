package com.barasa.speedy.user.infrastructure;

import com.barasa.speedy.session.infrastructure.SessionEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
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
    @Column(columnDefinition = "UUID")
    private UUID uuid;

    private String name;
    private String phone;

    @Column(columnDefinition = "jsonb")
    private String addinfo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<SessionEntity> sessions = new ArrayList<>();
}
