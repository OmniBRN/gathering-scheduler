package ro.tudorboureanu.gatheringschedule.Gathering;


import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GatheringService {
    private final GatheringRepository gatheringRepo;

    public GatheringService(GatheringRepository gatheringRepo) {
        this.gatheringRepo = gatheringRepo;
    }

    public Gathering createGathering(String gatheringName) {
        Gathering gathering = new Gathering(UUID.randomUUID(), gatheringName);
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