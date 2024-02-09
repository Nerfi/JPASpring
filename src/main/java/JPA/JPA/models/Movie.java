package JPA.JPA.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
//@Entity annotation defines that a class can be mapped to a table
@Table(name = "movies")

//PD:  @NotEmpty anotacion solo debe de ser usada en STRINGS, no en numbers
public class Movie {
    //@Id: This annotation specifies the primary key of the entity.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDNETITY es por mysql
    private Long movie_id;
    @NotEmpty
    @Size(min = 2, max = 120)
    private String title;
    //maybe this could be an array of authors
    @NotEmpty
    @Size(min = 2, max = 120)
    private String author;
    @NotEmpty
    @Size(min = 2, max = 120)
    private String country;
    @NotNull
    @Min(0) @Max(5)
    private int rating;

    @NotEmpty
    private String owner;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //Especifica el nombre de la columna que actúa como clave foránea en la tabla movies
    private User user;

    //necesario si marcamos la clase como @Entity
    public Movie(){}


    public Movie(String title, String author, String country, int rating, User user) {
        this.title = title;
        this.author = author;
        this.country = country;
        this.rating = rating;
        this.user = user;
        //this.owner = owner;
    }

    public Long getId(){
        return  this.movie_id;
    }

    public void setId(Long id) {
        this.movie_id= id;
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
        this.author = author;

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

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getOwner(){
        return this.owner;
    }




}
