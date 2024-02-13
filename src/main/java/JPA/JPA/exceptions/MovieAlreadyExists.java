package JPA.JPA.exceptions;

public class MovieAlreadyExists extends RuntimeException{
    private String message;

    public MovieAlreadyExists(String message){
        super(message);
        this.message = message;
    }

    public MovieAlreadyExists(){}
}
