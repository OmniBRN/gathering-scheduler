package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserRequestDTO;
import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserResponseDTO;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidPinException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidUsernameException;


@RestController
@RequestMapping("/api/gathering/{gatheringId}/user")
public class GatheringUserController {
    private final GatheringUserService gatheringUserService;
    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");

    public GatheringUserController(GatheringUserService gatheringUserService) {
        this.gatheringUserService = gatheringUserService;
    }

    @PostMapping("/")
    public ResponseEntity<GatheringUserResponseDTO> createGatheringUser(@PathVariable UUID gatheringId, @RequestBody GatheringUserRequestDTO request) {
        if (request.pin() == null || !PIN_PATTERN.matcher(request.pin()).matches()){
            throw new InvalidPinException("PIN must be a 4 digit combination");
        }
        
        if (request.username() == null || request.username().isBlank()) {
            throw new InvalidUsernameException("Name must not be empty");
        }

        if (request.username().length() > 20) {
            throw new InvalidUsernameException("Name must not exceed 20 characters");
        }


        GatheringUser newGatheringUser = gatheringUserService.createGatheringUser(gatheringId, request.username(), request.pin(), request.admin());
        return ResponseEntity.ok(new GatheringUserResponseDTO(newGatheringUser.getId().getGatheringId(), newGatheringUser.getId().getId(), newGatheringUser.isAdmin(), newGatheringUser.getUsername()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GatheringUserResponseDTO>> getAllUsersFromGathering(@PathVariable UUID gatheringId) {
        List<GatheringUserResponseDTO> allUsers =  gatheringUserService.getAllUsersFromGathering(gatheringId)
        .stream()
        .map(u -> new GatheringUserResponseDTO(
            u.getId().getGatheringId(),
            u.getId().getId(),
            u.isAdmin(),
            u.getUsername()
        )).toList();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("{userId}")
    public ResponseEntity<GatheringUserResponseDTO> getUserFromGathering(@PathVariable UUID gatheringId, @PathVariable UUID userId)
    {
        return gatheringUserService.getUserFromGathering(gatheringId, userId)
        .map(g -> ResponseEntity.ok(
            new GatheringUserResponseDTO(
                g.getId().getGatheringId(),
                g.getId().getId(),
                g.isAdmin(),
                g.getUsername()
            )))
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{userId}/grantAdmin")
    public ResponseEntity<GatheringUserResponseDTO> grantAdminToUser(@PathVariable UUID gatheringId, @PathVariable UUID userId) {
        return gatheringUserService.grantAdminToUser(gatheringId, userId)
        .map(g -> ResponseEntity.ok(
            new GatheringUserResponseDTO(
            g.getId().getGatheringId(),
            g.getId().getId(),
            g.isAdmin(),
            g.getUsername()
        )))
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{userId}/revokeAdmin")
    public ResponseEntity<GatheringUserResponseDTO> revokeAdminOfUser(@PathVariable UUID gatheringId, @PathVariable UUID userId) {
        return gatheringUserService.revokeAdminOfUser(gatheringId, userId)
        .map(g -> ResponseEntity.ok(
            new GatheringUserResponseDTO(
            g.getId().getGatheringId(),
            g.getId().getId(),
            g.isAdmin(),
            g.getUsername()
        )))
        .orElseGet(() -> ResponseEntity.notFound().build());
    }






}
