package ro.tudorboureanu.gatheringschedule.Gathering;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringRepository extends JpaRepository<Gathering, UUID>{ 
    Optional<Gathering> findById(UUID id);
}
