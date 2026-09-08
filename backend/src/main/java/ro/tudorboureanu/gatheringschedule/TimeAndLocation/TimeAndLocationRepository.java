package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeAndLocationRepository extends JpaRepository<TimeAndLocation, UUID>{
    Optional<TimeAndLocation> findById(UUID id);
    Optional<TimeAndLocation> findByIdAndGatheringId(UUID id, UUID gatheringId);
    Optional<TimeAndLocation> findByGatheringIdAndPrimaryLocationTrue(UUID gatheringId);
    List<TimeAndLocation> findByGatheringId(UUID gatheringId);
}
