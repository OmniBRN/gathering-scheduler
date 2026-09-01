package ro.tudorboureanu.gatheringschedule.GatheringUser.DTOs;

import java.util.UUID;

public record GatheringUserResponseDTO (UUID gatheringId, UUID userId, Boolean isAdmin, String username){
}
