package ro.tudorboureanu.gatheringschedule.GatheringUser;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="gathering_members")
public class GatheringUser {
    @Id 
    private GatheringUserId id;

    @Column(name = "creator")
    private Boolean adminStatus;

    @Column(name= "member_name")
    private String username; 

    private String hashedPin;

    protected GatheringUser() {

    }

    @JsonCreator
    public GatheringUser
    (@JsonProperty("id")             UUID id,
     @JsonProperty("gatheringId")    UUID gatheringId,
     @JsonProperty("username")       String username,
     @JsonProperty("admin")          Boolean isAdmin, 
     @JsonProperty("hashedPin")      String hashedPin){
        this.id = new GatheringUserId(gatheringId, id);
        this.username = username;
        this.adminStatus = isAdmin;
        this.hashedPin = hashedPin;  
    }

    public GatheringUserId getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getHashedPin() { return this.hashedPin; }
    public Boolean isAdmin() { return this.adminStatus; }

    public void setUsername(String newUsername) { this.username = newUsername; }
    public void grantAdmin() { this.adminStatus = true; }
    public void revokeAdmin() { this.adminStatus = false; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GatheringUser other)) return false;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
