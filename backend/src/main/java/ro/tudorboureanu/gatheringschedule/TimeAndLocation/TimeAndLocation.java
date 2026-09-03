package ro.tudorboureanu.gatheringschedule.TimeAndLocation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_and_location")
public class TimeAndLocation {
    @Id
    private UUID id;

    private UUID gatheringId;

    private UUID userId;

    private Instant startTime;

    private Instant endTime;

    // @Column(name = "gathering_location_longitude")
    private BigDecimal gatheringLocationLongitude;

    private BigDecimal gatheringLocationLatitude;

    private Boolean primaryLocation;

    protected TimeAndLocation() {

    }

    @JsonCreator
    public TimeAndLocation
    (@JsonProperty("id")                            UUID id,
     @JsonProperty("gatheringId")                   UUID gatheringId,
     @JsonProperty("userId")                        UUID userId,
     @JsonProperty("startTime")                     Instant startTime,
     @JsonProperty("endTime")                       Instant endTime,
     @JsonProperty("gatheringLocationLongitude")    BigDecimal gatheringLocationLongitude,
     @JsonProperty("gatheringLocationLatitude")     BigDecimal gatheringLocationLatitude,
     @JsonProperty("primaryLocation")               Boolean primaryLocation) {
        this.id = id;
        this.gatheringId = gatheringId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gatheringLocationLongitude = gatheringLocationLongitude;
        this.gatheringLocationLatitude = gatheringLocationLatitude;
        this.primaryLocation = primaryLocation;
     }
    
    public UUID getId() { return this.id; }
    public UUID getGatheringId() { return this.gatheringId; }
    public UUID getUserId() { return this.userId; }
    public Instant getStartTime() { return this.startTime; }
    public Instant getEndTime() { return this.endTime; }
    public BigDecimal getLocationLongitude() { return this.gatheringLocationLongitude; }
    public BigDecimal getLocationLatitude() { return this.gatheringLocationLatitude; }
    public Boolean isPrimaryLocation() { return this.primaryLocation; }

    public void setTimeInterval(Instant startTime, Instant endTime) { 
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setLocationCoordonates(BigDecimal longitude, BigDecimal latitude) {
        this.gatheringLocationLongitude = longitude;
        this.gatheringLocationLatitude = latitude;
    }

    public void makePrimaryLocation() { this.primaryLocation = true; }
    public void revokePrimaryLocation() { this.primaryLocation = false;} 
}
