package ro.tudorboureanu.gatheringschedule.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ro.tudorboureanu.gatheringschedule.Gathering.exceptions.InvalidGatheringNameException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidPinException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.InvalidUsernameException;
import ro.tudorboureanu.gatheringschedule.GatheringUser.exceptions.LastAdminException;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.exceptions.InvalidLocationException;
import ro.tudorboureanu.gatheringschedule.TimeAndLocation.exceptions.InvalidTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(LastAdminException.class)
    public ProblemDetail handleLastAdmin(LastAdminException ex)  {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Cannot remove the last admin");
        return problem;
    }

    @ExceptionHandler(InvalidPinException.class)
    public ProblemDetail handleInvalidPin(InvalidPinException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Pin");
        return problem;
    } 

    @ExceptionHandler(InvalidUsernameException.class)
    public ProblemDetail handleInvalidUsername(InvalidUsernameException ex)
    {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Username");
        return problem;
    }

    @ExceptionHandler(InvalidGatheringNameException.class)
    public ProblemDetail handleInvalidGatheringName(InvalidGatheringNameException ex)
    {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Gathering Name");
        return problem;
    }
    
    @ExceptionHandler(InvalidTimeException.class)
    public ProblemDetail handleInvalidLocationTime(InvalidTimeException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Location Time");
        return problem;
    }

    @ExceptionHandler(InvalidLocationException.class)
    public ProblemDetail handleInvalidLocationException(InvalidLocationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Invalid Coordinates");
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail fallBack(Exception ex) {
        ProblemDetail problem = ProblemDetail.forStatus(500);
        log.error("Unhandled Exception", ex);
        problem.setTitle("Unhandled Exception");
        return problem;
    }

}
