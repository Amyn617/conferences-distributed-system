package ma.enset.keynoteservice.repositories;

import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface KeynoteRepository extends JpaRepository<Keynote, Long> {
    boolean existsByEmail(String email);
    Optional<Keynote> findByEmail(String email);
}