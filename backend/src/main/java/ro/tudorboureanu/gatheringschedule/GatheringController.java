package ro.tudorboureanu.gatheringschedule;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gathering")
public class GatheringController {
    @GetMapping("/{requestId}")
    private ResponseEntity<Gathering> findById(@PathVariable String requestId) {
        if(requestId.equals("e86294a0-ef5d-4afc-81e8-74ca97c94789"))
        {
            UUID gatheringId = UUID.fromString(requestId);
            Gathering gathering = new Gathering(gatheringId, UUID.randomUUID(), "Temporary");
            return ResponseEntity.ok(gathering);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
