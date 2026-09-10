package ro.tudorboureanu.gatheringschedule.TimeAndLocationVotes;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_and_location_votes")
public class TimeAndLocationVotes {
    
    @Id
    private TimeAndLocationVotesId id;

    private UUID gatheringId;

    protected TimeAndLocationVotes(){
    }

    public TimeAndLocationVotes(UUID TALId, UUID userId, UUID gatheringId) {
        this.id = new TimeAndLocationVotesId(TALId, userId);
        this.gatheringId = gatheringId;
    }

    public TimeAndLocationVotesId getId() { return this.id; }
    public UUID getTALId() { return this.id.getTALId(); }
    public UUID getUserId() { return this.id.getUserId(); }
    public UUID getGatheringId() { return this.gatheringId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof TimeAndLocationVotes other)) return false;
        return id != null && gatheringId != null && id.equals(other.getId()) && gatheringId.equals(other.getGatheringId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
