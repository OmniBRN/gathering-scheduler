package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TimeAndLocationService {
    private final TimeAndLocationRepository timeAndLocationRepository;
    public TimeAndLocationService(TimeAndLocationRepository timeAndLocationRepository) {
        this.timeAndLocationRepository = timeAndLocationRepository;
    }

    public TimeAndLocation createTAL(UUID gatheringId, UUID userId, Instant startTime, Instant endTime, BigDecimal longitude, BigDecimal latitude)
    {
        Boolean isPrimaryLocation = false;
        long count = timeAndLocationRepository.findByGatheringId(gatheringId).size();
        if(count == 0){
            isPrimaryLocation = true;
        }
        UUID newTALId = UUID.randomUUID();
        TimeAndLocation newTAL = new TimeAndLocation
        (newTALId,
         gatheringId, 
         userId, 
         startTime, 
         endTime, 
         longitude, 
         latitude,
         isPrimaryLocation
        );

        timeAndLocationRepository.save(newTAL);
        return newTAL;
    }

    public List<TimeAndLocation> getAllTAL(UUID gatheringId) {
        return timeAndLocationRepository.findByGatheringId(gatheringId);
    }

    public Optional<TimeAndLocation> getTALById(UUID gatheringId, UUID TALId){
        return timeAndLocationRepository.findByIdAndGatheringId(TALId, gatheringId);
    }

    public Optional<TimeAndLocation> modifyTAL(UUID gatheringId, UUID TALId, Instant startTime, Instant endTime, BigDecimal longitude, BigDecimal latitude, Boolean makePrimary){
        Optional<TimeAndLocation> modifiedTAL = timeAndLocationRepository.findByIdAndGatheringId(TALId, gatheringId);
        modifiedTAL = modifiedTAL.map(t -> {
            t.setLocationCoordonates(longitude, latitude);
            t.setTimeInterval(startTime, endTime);
            if(makePrimary == true){
                timeAndLocationRepository.findByGatheringIdAndPrimaryLocationTrue(gatheringId).map(op -> {
                    op.revokePrimaryLocation();
                    return timeAndLocationRepository.save(op);
                });
                t.makePrimaryLocation();
            }
            return timeAndLocationRepository.save(t);
        });
        return modifiedTAL;
    }

    public Optional<TimeAndLocation> deleteTAL(UUID gatheringId, UUID TALId) {
        return timeAndLocationRepository.findByIdAndGatheringId(TALId, gatheringId).map(t -> {
            timeAndLocationRepository.delete(t);
            return t;
        });
    }
    
    
}
