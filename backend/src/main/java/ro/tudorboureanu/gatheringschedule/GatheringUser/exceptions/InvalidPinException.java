package ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions;

public class InvalidPinException extends RuntimeException{
    public InvalidPinException(String message) {
        super(message);
    }
}
