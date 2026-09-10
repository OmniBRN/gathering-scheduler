package ro.tudorboureanu.gatheringschedule.TimeAndLocationVotes;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable 
public class TimeAndLocationVotesId implements Serializable{
    private UUID TALId;
    private UUID userId; 

    protected TimeAndLocationVotesId(){
    }

    public TimeAndLocationVotesId(UUID TALId, UUID userId){
        this.TALId = TALId;
        this.userId = userId;
    }

    public UUID getTALId() { return this.TALId; }
    public UUID getUserId() { return this.userId; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof TimeAndLocationVotesId other)) return false;
        return TALId != null && userId != null && TALId.equals(other.getTALId()) && userId.equals(other.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
