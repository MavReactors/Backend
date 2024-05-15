package Mavreactors.app.Repository;

import Mavreactors.app.Model.Prendas;
import Mavreactors.app.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrendaRepository  extends JpaRepository<Prendas, Long> {
    List<Prendas> findByUser(User user);
}
