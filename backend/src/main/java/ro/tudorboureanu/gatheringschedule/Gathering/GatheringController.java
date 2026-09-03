package ro.tudorboureanu.gatheringschedule.Gathering;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.tudorboureanu.gatheringschedule.Gathering.exceptions.InvalidGatheringNameException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserRequestDTO;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidPinException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidUsernameException;


@RestController
@RequestMapping("/api/gathering")
public class GatheringController {

    private final GatheringService gatheringService;
    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @PostMapping("/")
    private ResponseEntity<Gathering> createGathering(@RequestBody String gatheringName, GatheringUserRequestDTO gatheringUser){
        if (gatheringUser.pin() == null || !PIN_PATTERN.matcher(gatheringUser.pin()).matches()){
            throw new InvalidPinException("PIN must be a 4 digit combination");
        }
        
        if (gatheringUser.username() == null || gatheringUser.username().isBlank()) {
            throw new InvalidUsernameException("Username must not be empty");
        }

        if (gatheringUser.username().length() > 20) {
            throw new InvalidUsernameException("Username must not exceed 20 characters");
        }

        if (gatheringName == null || gatheringName.isBlank()) {
            throw new InvalidGatheringNameException("Gathering name must not be empty");
        }

        if (gatheringName.length() > 128) {
            throw new InvalidGatheringNameException("Gathering name must not exceed 128 characters");
        }
        
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