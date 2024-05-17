package Mavreactors.app.Repository;

import Mavreactors.app.Model.Session;
import Mavreactors.app.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}
