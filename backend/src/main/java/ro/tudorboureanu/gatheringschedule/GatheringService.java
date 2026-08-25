package ro.tudorboureanu.gatheringschedule;


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

    public Optional<Gathering> getGathering(String id) {
        return gatheringRepo.findById(UUID.fromString(id));
    }

}