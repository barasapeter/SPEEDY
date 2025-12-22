package com.barasa.speedy.session.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SessionReportJpaRepository extends JpaRepository<SessionReportEntity, UUID> {
}
