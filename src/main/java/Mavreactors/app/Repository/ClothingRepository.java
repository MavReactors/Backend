package Mavreactors.app.Repository;

import Mavreactors.app.Model.Clothing;
import Mavreactors.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, UUID> {
    List<Clothing> findByUser(User user);
    List<Clothing> findByIsFavoriteTrueAndUser(User user);
}
