package Mavreactors.app.Repository;

import Mavreactors.app.Model.Outfit;
import Mavreactors.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OutfitRepository extends JpaRepository<Outfit, UUID> {
    List<Outfit> findByUser(User user);
    List<Outfit> findByIsPublicTrue();

    @Query("SELECT o FROM Outfit o WHERE o.isPublic = true ORDER BY o.voteCount DESC")
    List<Outfit> findPublicOutfitsOrderedByVoteCount();
}
