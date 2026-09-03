package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gathering/{gatheringId}/tal")
public class TimeAndLocationController {
    private final TimeAndLocationService timeAndLocationService;

    public TimeAndLocationController(TimeAndLocationService timeAndLocationService) {
        this.timeAndLocationService = timeAndLocationService;
    }

    
}
