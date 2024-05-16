package Mavreactors.app.Repository;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutfitRepository extends JpaRepository<Outfit, UUID> {
    List<Outfit> findByUser(User user);
}
