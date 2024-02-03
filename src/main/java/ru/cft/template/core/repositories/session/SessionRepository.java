package ru.cft.template.core.repositories.session;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.models.Session;
import ru.cft.template.core.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SessionRepository  extends JpaRepository<Session, UUID> {
    List<Session> findByUser(User user);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.active = true ORDER BY s.expirationTime DESC")
    List<Session> findLatestActiveByUser(@Param("user") User user);

}
