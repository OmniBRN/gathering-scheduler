package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;


public interface GatheringUserRepository extends JpaRepository<GatheringUser, GatheringUserId>{
    Optional<GatheringUser> findById(GatheringUserId id);
    List<GatheringUser> findByid_GatheringId(UUID gatheringId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from GatheringUser m where m.id.gatheringId = :gatheringId")
    List<GatheringUser> findAllByGatheringIdForUpdate(@Param("gatheringId") UUID gatheringId);
} 