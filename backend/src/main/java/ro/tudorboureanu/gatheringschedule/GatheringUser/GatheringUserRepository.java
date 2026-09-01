package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface GatheringUserRepository extends JpaRepository<GatheringUser, GatheringUserId>{
    Optional<GatheringUser> findById(GatheringUserId id);
} 