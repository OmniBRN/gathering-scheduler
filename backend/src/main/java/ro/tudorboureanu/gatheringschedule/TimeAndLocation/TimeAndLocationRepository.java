package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeAndLocationRepository extends JpaRepository<TimeAndLocation, UUID>{
    Optional<TimeAndLocation> findById(UUID id);
}
