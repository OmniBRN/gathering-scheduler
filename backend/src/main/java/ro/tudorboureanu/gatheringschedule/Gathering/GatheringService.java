package ro.tudorboureanu.gatheringschedule.Gathering;


import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ro.tudorboureanu.gatheringschedule.GatheringUser.GatheringUserRepository;

@Service
public class GatheringService {
    private final GatheringRepository gatheringRepo;
    private final GatheringUserRepository gatheringUserRepo;
    public GatheringService(GatheringRepository gatheringRepo, GatheringUserRepository gatheringUserRepo) {
        this.gatheringRepo = gatheringRepo;
        this.gatheringUserRepo = gatheringUserRepo;
    }

    public Gathering createGathering(String gatheringName) {
        UUID gatheringId = UUID.randomUUID();
        Gathering gathering = new Gathering(gatheringId, gatheringName);
        return gatheringRepo.save(gathering);
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