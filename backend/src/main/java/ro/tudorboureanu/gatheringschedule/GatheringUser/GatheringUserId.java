package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class GatheringUserId implements Serializable {
    private UUID gatheringId;
    private UUID id;

    protected GatheringUserId(){
    }

    public GatheringUserId(UUID gatheringId, UUID id){
        this.gatheringId = gatheringId;
        this.id = id;
    }

    public UUID getGatheringId() { return this.gatheringId; }
    public UUID getId() { return this.id; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof GatheringUserId other)) return false;
        return id != null && gatheringId != null && id.equals(other.getId()) && gatheringId.equals(other.getGatheringId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}