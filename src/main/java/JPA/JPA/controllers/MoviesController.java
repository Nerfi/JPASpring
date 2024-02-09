package JPA.JPA.controllers;

import JPA.JPA.models.Movie;
import JPA.JPA.models.User;
import JPA.JPA.repository.MovieRepository;
import JPA.JPA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;



import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")

public class MoviesController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Movie>> findAll() {
    // de momento no tenemos nada aqui
        List<Movie> movies = movieRepository.findAll();
       if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/add")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> createMovie(@RequestBody Movie movie, Principal principal) {
        System.out.println("entramos aqui ");
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());

        if(optionalUser.isPresent()) {
            User user = optionalUser.get(); // obtenemos el usuario
            // creamos una nueva movie
            Movie movietoSave = new Movie(movie.getTitle(), movie.getAuthor(), movie.getCountry(), movie.getRating(), user);
            //guardamos
            movieRepository.save(movietoSave);

            // Actualizar la lista de películas del usuario
            user.getMoviesList().add(movietoSave);
            userRepository.save(user);

          //  System.out.println(user.getMoviesList().size() + " movies by user"); works

            return ResponseEntity.ok().build();
        }

         return ResponseEntity.notFound().build();


//        System.out.println("*******************");
//
//        System.out.println(movie.getTitle() + " title"); // funciona
//        System.out.println("*******************");
//
//        System.out.println(movie.getRating() + " rating"); // funciona
//
//        System.out.println("************************");
//        System.out.println(movie.getCountry() + " country");
//        System.out.println("***************************");
//
//        System.out.println(movie.getAuthor() +  " author"); // no llega
        //System.out.println(principal.getName() + " the principal");// funciona
       // return null;
    }



}
