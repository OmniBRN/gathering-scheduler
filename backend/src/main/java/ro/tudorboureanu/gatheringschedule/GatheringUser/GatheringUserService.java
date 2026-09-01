package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class GatheringUserService {
    private final GatheringUserRepository gatheringUserRepo;
    private final PasswordEncoder arg2pwencoder;
    public GatheringUserService(GatheringUserRepository gatheringUserRepo, PasswordEncoder arg2pwencoder){
        this.gatheringUserRepo = gatheringUserRepo;
        this.arg2pwencoder = arg2pwencoder;
    }

    public GatheringUser createGatheringUser(UUID gatheringId, String username, String pin, Boolean isAdmin){
        String hashedPin = arg2pwencoder.encode(pin);

        UUID userId = UUID.randomUUID();

        GatheringUser newGatheringUser = new GatheringUser(userId, gatheringId, username, isAdmin, hashedPin);
        return gatheringUserRepo.save(newGatheringUser);
    }

    public List<GatheringUser> getAllUsersFromGathering(UUID gatheringId) {
        return gatheringUserRepo.findByid_GatheringId(gatheringId);
    }

    public Optional<GatheringUser> getUserFromGathering(UUID gatheringId, UUID userId){
        return gatheringUserRepo.findById(new GatheringUserId(gatheringId, userId));
    }

}
