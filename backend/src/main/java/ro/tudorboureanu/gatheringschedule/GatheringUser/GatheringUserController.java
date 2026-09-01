package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserRequestDTO;
import ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs.GatheringUserResponseDTO;


@RestController
@RequestMapping("/api/gathering/{gatheringId}/user")
public class GatheringUserController {
    private final GatheringUserService gatheringUserService;
    public GatheringUserController(GatheringUserService gatheringUserService) {
        this.gatheringUserService = gatheringUserService;
    }

    @PostMapping("/")
    public ResponseEntity<GatheringUserResponseDTO> createGatheringUser(@PathVariable UUID gatheringId, @RequestBody GatheringUserRequestDTO request) {
        GatheringUser newGatheringUser = gatheringUserService.createGatheringUser(gatheringId, request.username(), request.pin(), request.admin());
        
        return ResponseEntity.ok(new GatheringUserResponseDTO(newGatheringUser.getId().getGatheringId(), newGatheringUser.getId().getId(), newGatheringUser.isAdmin(), newGatheringUser.getUsername()));
    }




}
