package ro.tudorboureanu.gatheringschedule.Gathering;


import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ro.tudorboureanu.gatheringschedule.GatheringUser.GatheringUser;
import ro.tudorboureanu.gatheringschedule.GatheringUser.GatheringUserRepository;

@Service
public class GatheringService {
    private final GatheringRepository gatheringRepo;
    private final GatheringUserRepository gatheringUserRepo;
    private final PasswordEncoder arg2pwencoder;
    public GatheringService(GatheringRepository gatheringRepo, GatheringUserRepository gatheringUserRepo, PasswordEncoder arg2pwencoder) {
        this.gatheringRepo = gatheringRepo;
        this.gatheringUserRepo = gatheringUserRepo;
        this.arg2pwencoder = arg2pwencoder;
    }

    public Gathering createGathering(String gatheringName, String gatheringCreatorUsername, String gatheringCreatorPin) {
        UUID gatheringId = UUID.randomUUID();
        Boolean isAdmin = true;
        String gatheringCreatorHashedPin = arg2pwencoder.encode(gatheringCreatorPin);

        Gathering gathering = new Gathering(gatheringId, gatheringName);
        gatheringRepo.save(gathering);

        GatheringUser gatheringUser = new GatheringUser(UUID.randomUUID(), gatheringId, gatheringCreatorUsername, isAdmin, gatheringCreatorHashedPin);
        gatheringUserRepo.save(gatheringUser);

        return gathering;
    }

    public Optional<Gathering> getGathering(UUID id) {
        return gatheringRepo.findById(id);
    }

    public Optional<Gathering> modifyGathering(UUID id, String newGatheringName) {
        Optional<Gathering> originalGathering = gatheringRepo.findById(id);
        Optional<Gathering> modifiedGathering = originalGathering.map(g -> {
            g.setGatheringName(newGatheringName);
            return gatheringRepo.save(g);
        });
        return modifiedGathering;
    }

    public Optional<Gathering> deleteGathering(UUID id) {
        Optional<Gathering> res = gatheringRepo.findById(id).map(g-> {gatheringRepo.delete(g); return g;});
        return res;
    }

}