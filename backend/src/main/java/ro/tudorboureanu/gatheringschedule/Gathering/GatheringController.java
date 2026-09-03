package ro.tudorboureanu.gatheringschedule.Gathering;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserRequestDTO;


@RestController
@RequestMapping("/api/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @PostMapping("/")
    private ResponseEntity<Gathering> createGathering(@RequestBody String gatheringName, @RequestBody GatheringUserRequestDTO gatheringUser){
        Gathering newGathering = gatheringService.createGathering(gatheringName, gatheringUser.username(), gatheringUser.pin());
        return ResponseEntity.ok(newGathering);
    }

    @GetMapping("/{requestId}")
    private ResponseEntity<Gathering> findById(@PathVariable UUID requestId) {
        return gatheringService.getGathering(requestId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{requestId}")
    private ResponseEntity<Gathering> modifyGathering(@PathVariable UUID requestId, @RequestBody String newGatheringName) {

        return gatheringService.modifyGathering(requestId, newGatheringName)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{requestId}")
    private ResponseEntity<Void> deleteGathering(@PathVariable UUID requestId){
        if (gatheringService.deleteGathering(requestId).isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }


}