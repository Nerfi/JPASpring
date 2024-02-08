package JPA.JPA.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
//@Entity annotation defines that a class can be mapped to a table
@Table(name = "movies")
public class Movie {
    //@Id: This annotation specifies the primary key of the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDNETITY es por mysql
    private Long id;
    @NotEmpty
    @Min(2) @Max(120)
    private String title;
    //maybe this could be an array of authors
    @NotEmpty
    @Min(2) @Max(120)
    private String author;
    @NotEmpty
    @Min(2) @Max(120)
    private String country;
    @NotEmpty
    @Min(0) @Max(5)
    private int rating;

    //necesario si marcamos la clase como @Entity
    public Movie(){}


    public Movie(String title, String author, String country, int rating){
        this.title = title;
        this.author = author;
        this.country = country;
        this.rating = rating;
    }

    public Long getId(){
        return  this.id;
    }

    public void setId(Long id) {
        this.id= id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;

    }
    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author) {
        this.title = author;

    }


    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating(){
        return this.rating;
    }

    public void setRating(int rating){
        this.rating =rating;
    }




}