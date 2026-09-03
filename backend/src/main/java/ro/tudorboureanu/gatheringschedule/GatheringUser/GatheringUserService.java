package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.LastAdminException;


@Service
public class GatheringUserService {
    private final GatheringUserRepository gatheringUserRepo;
    private final PasswordEncoder arg2pwencoder;
    public GatheringUserService(GatheringUserRepository gatheringUserRepo, PasswordEncoder arg2pwencoder){
        this.gatheringUserRepo = gatheringUserRepo;
        this.arg2pwencoder = arg2pwencoder;
    }

    public GatheringUser createGatheringUser(UUID gatheringId, String username, String pin){
        String hashedPin = arg2pwencoder.encode(pin);

        UUID userId = UUID.randomUUID();
        Boolean isAdmin = false;
        GatheringUser newGatheringUser = new GatheringUser(userId, gatheringId, username, isAdmin, hashedPin);
        return gatheringUserRepo.save(newGatheringUser);
    }

    public List<GatheringUser> getAllUsersFromGathering(UUID gatheringId) {
        return gatheringUserRepo.findByid_GatheringId(gatheringId);
    }

    public Optional<GatheringUser> getUserFromGathering(UUID gatheringId, UUID userId){
        return gatheringUserRepo.findById(new GatheringUserId(gatheringId, userId));
    }

    public Optional<GatheringUser> grantAdminToUser(UUID gatheringId, UUID userId){
        Optional<GatheringUser> user = gatheringUserRepo.findById(new GatheringUserId(gatheringId, userId));
        user = user.map(g -> {
            g.grantAdmin();
            return gatheringUserRepo.save(g);
        });
        return user;
    }

    @Transactional
    public Optional<GatheringUser> revokeAdminOfUser(UUID gatheringId, UUID userId){
        List<GatheringUser> members = gatheringUserRepo.findAllByGatheringIdForUpdate(gatheringId);
        Optional<GatheringUser> target = members.stream()
                .filter(m -> m.getId().getId().equals(userId))
                .findFirst();
        if (target.isEmpty()) return Optional.empty();

        long adminCount = members.stream().filter(m -> Boolean.TRUE.equals(m.isAdmin())).count();

        GatheringUser user = target.get();
        if (Boolean.TRUE.equals(user.isAdmin()) && adminCount == 1) {
            throw new LastAdminException("There must be atleast one admin for each gathering");
        }

        user.revokeAdmin();
        return Optional.of(user);

    }

}
