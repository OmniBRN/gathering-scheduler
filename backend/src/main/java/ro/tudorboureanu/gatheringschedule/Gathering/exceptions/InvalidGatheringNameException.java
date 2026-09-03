package ro.tudorboureanu.gatheringschedule.Gathering.exceptions;

public class InvalidGatheringNameException extends RuntimeException{
    public InvalidGatheringNameException(String message) {
        super(message);
    }
}
