package ro.tudorboureanu.gatheringschedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @GetMapping("/{requestId}")
    private ResponseEntity<Gathering> findById(@PathVariable String requestId) {
        return gatheringService.getGathering(requestId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    private ResponseEntity<Gathering> createGathering(@RequestBody String gatheringName){
        Gathering newGathering = gatheringService.createGathering(gatheringName);
        return ResponseEntity.ok(newGathering);
    }


}