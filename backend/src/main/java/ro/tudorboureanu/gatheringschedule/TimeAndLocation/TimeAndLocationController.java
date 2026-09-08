package ro.tudorboureanu.gatheringschedule.TimeAndLocation;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;

import ro.tudorboureanu.gatheringschedule.Gathering.GatheringService;
import ro.tudorboureanu.gatheringschedule.GatheringUser.GatheringUserService;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.DTO.TimeAndLocationRequestDTO;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.DTO.TimeAndLocationUpdateDTO;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.exceptions.InvalidLocationException;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.exceptions.InvalidTimeException;

@RestController
@RequestMapping("/api/gathering/{gatheringId}/tal")
public class TimeAndLocationController {
    private final TimeAndLocationService timeAndLocationService;
    private final GatheringService gatheringService;
    private final GatheringUserService gatheringUserService;

    public TimeAndLocationController(TimeAndLocationService timeAndLocationService, GatheringService gatheringService, GatheringUserService gatheringUserService) {
        this.timeAndLocationService = timeAndLocationService;
        this.gatheringService = gatheringService;
        this.gatheringUserService = gatheringUserService;
    }

    @PostMapping("/")
    public ResponseEntity<TimeAndLocation> createTAL(@PathVariable UUID gatheringId, @RequestBody TimeAndLocationRequestDTO request){
        if (!gatheringService.getGathering(gatheringId).isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(!gatheringUserService.getUserFromGathering(gatheringId, request.userId()).isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(request.endTime().compareTo(request.startTime()) < 0){
            throw new InvalidTimeException("End time cannot be before the start time");
        }

        if(request.latitude().doubleValue() < -90 || request.latitude().doubleValue() > 90 || request.longitude().doubleValue() < -180 | request.longitude().doubleValue() > 180)
        {
            throw new InvalidLocationException("Invalid Coordinates");
        }

        TimeAndLocation newTAL = timeAndLocationService.createTAL
        (
         gatheringId, 
         request.userId(), 
         request.startTime(), 
         request.endTime(), 
         request.longitude(), 
         request.latitude()
         );
        
         return ResponseEntity.ok(newTAL);
       
    }

    @GetMapping("/all")
    public ResponseEntity<List<TimeAndLocation>> getAllTAL(@PathVariable UUID gatheringId){
        return ResponseEntity.ok(timeAndLocationService.getAllTAL(gatheringId));
    }

    @GetMapping("/{TALId}")
    public ResponseEntity<TimeAndLocation> getTALById(@PathVariable UUID gatheringId, @PathVariable UUID TALId){
        return timeAndLocationService.getTALById(gatheringId, TALId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{TALId}")
    public ResponseEntity<TimeAndLocation> modifyTAL(@PathVariable UUID gatheringId, @PathVariable UUID TALId, @RequestBody TimeAndLocationUpdateDTO request) {

        if (!gatheringService.getGathering(gatheringId).isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(!gatheringUserService.getUserFromGathering(gatheringId, request.userId()).isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(request.endTime().compareTo(request.startTime()) < 0){
            throw new InvalidTimeException("End time cannot be before the start time");
        }

        if(request.latitude().doubleValue() < -90 || request.latitude().doubleValue() > 90 || request.longitude().doubleValue() < -180 || request.longitude().doubleValue() > 180)
        {
            throw new InvalidLocationException("Invalid Coordinates");
        }
        return timeAndLocationService.modifyTAL
        (gatheringId,
         TALId,
         request.startTime(),
         request.endTime(),
         request.longitude(),
         request.latitude(),
         request.primaryLocation()
         ).map(ResponseEntity::ok)
         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{TALId}")
    public ResponseEntity<Void> deleteTAL(@PathVariable UUID gatheringId, @PathVariable UUID TALId){
        timeAndLocationService.deleteTAL(gatheringId, TALId);
        return ResponseEntity.ok().build();

    }

    
}
