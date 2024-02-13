package JPA.JPA.exceptions;

import java.util.Date;

/*
we created this class in order to have custom error
handling errors instead of the default given by spring
 */
public class ErrorMessageException {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;



    public ErrorMessageException(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
