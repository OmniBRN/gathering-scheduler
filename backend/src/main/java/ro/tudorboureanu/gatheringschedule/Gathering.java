package ro.tudorboureanu.gatheringschedule;

import java.util.UUID;

public record Gathering(UUID id, UUID creatorId, String gatheringName) {
}
