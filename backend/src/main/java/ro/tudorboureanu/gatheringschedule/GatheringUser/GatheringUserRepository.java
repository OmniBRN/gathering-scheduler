package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GatheringUserRepository extends JpaRepository<GatheringUser, GatheringUserId>{
    Optional<GatheringUser> findById(GatheringUserId id);
    List<GatheringUser> findByid_GatheringId(UUID id);
} 