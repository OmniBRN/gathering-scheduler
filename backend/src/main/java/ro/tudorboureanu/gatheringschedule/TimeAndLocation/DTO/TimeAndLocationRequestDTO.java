package ro.tudorboureanu.gatheringschedule.TimeAndLocation.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TimeAndLocationRequestDTO(UUID userId, Instant startTime, Instant endTime, BigDecimal longitude, BigDecimal latitude) {
}
