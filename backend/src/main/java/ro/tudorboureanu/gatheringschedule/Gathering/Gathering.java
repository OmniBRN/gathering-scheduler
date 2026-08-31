package ro.tudorboureanu.gatheringschedule.Gathering;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Gathering {

    @Id
    private UUID id;

    private String gatheringName;

    protected Gathering() {
    }

    @JsonCreator
    public Gathering(@JsonProperty("id") UUID id,
                     @JsonProperty("gatheringName") String gatheringName) {
        this.id = id;
        this.gatheringName = gatheringName;
    }

    public UUID getId() { return this.id; }
    public String getGatheringName() { return this.gatheringName; }

    public void setGatheringName(String newGatheringName) { this.gatheringName = newGatheringName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gathering other)) return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}