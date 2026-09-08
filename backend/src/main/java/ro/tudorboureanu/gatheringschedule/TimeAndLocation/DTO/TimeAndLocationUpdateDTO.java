package ro.tudorboureanu.gatheringschedule.TimeAndLocation.DTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TimeAndLocationUpdateDTO(UUID userId, Instant startTime, Instant endTime, BigDecimal longitude, BigDecimal latitude, Boolean primaryLocation) {
}
