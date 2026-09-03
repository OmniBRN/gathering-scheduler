package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import org.springframework.stereotype.Service;

@Service
public class TimeAndLocationService {
    private final TimeAndLocationRepository timeAndLocationRepository;
    public TimeAndLocationService(TimeAndLocationRepository timeAndLocationRepository) {
        this.timeAndLocationRepository = timeAndLocationRepository;
    }
    
}
