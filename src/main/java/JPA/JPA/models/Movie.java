package JPA.JPA.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// lombok
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "user")
@AllArgsConstructor
/*

La anotación @AllArgsConstructor en Lombok es una anotación que se utiliza para generar automáticamente un constructor que incluye todos los campos de la clase.
En otras palabras, Lombok generará un constructor que acepta todos los campos de la clase como parámetros y los inicializa.
 */

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
//https://stackoverflow.com/questions/65930344/springboot-onetomany-infinite-loop-with-lombok
    @JsonIgnore // new addition bidireccional error lombok
    @ManyToOne
    @JoinColumn(name = "user_id")
    //Especifica el nombre de la columna que actúa como clave foránea en la tabla movies
    private User user;

    //necesario si marcamos la clase como @Entity
    public Movie() {

    }


    // hemos añadido este metodo ya que JPA parece parece que solo entiende el nombre del campo cuando se llama ID(en minisculas) a secas
    // al haberlo llamado movie_id por las convenciones que tiene no puedo usar metodos del repositorio que sean de buscar mediante el ID, que es el
    // nombre del field in the class
    public Long getId(){
        return  this.movie_id;
    }




}
