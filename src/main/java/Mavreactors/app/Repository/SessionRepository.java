package Mavreactors.app.Repository;

import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    Session findByUser(User user);
    Session findByToken(UUID uuid);
    Session findTopByUserOrderByTimestampDesc(User user);
}
