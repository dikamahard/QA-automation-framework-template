package helper;

public class FlowException extends RuntimeException{

    public FlowException(String flowName, Throwable cause) {
        super("Flow failed: " + flowName, cause);
    }
}
