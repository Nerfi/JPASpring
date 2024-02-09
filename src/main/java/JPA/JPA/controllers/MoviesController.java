package JPA.JPA.controllers;

import JPA.JPA.models.Movie;
import JPA.JPA.models.User;
import JPA.JPA.repository.MovieRepository;
import JPA.JPA.repository.UserRepository;
import JPA.JPA.security.jwt.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;



import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createMovie(@RequestBody Movie movie, Principal principal,  UriComponentsBuilder ucb) {

        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());

        //check if we already have a movie with such title
        if(movieRepository.existsByTitle(movie.getTitle())) {

            return ResponseEntity.badRequest().body(new MessageResponse("Error: movie name already created!"));
        }

        if(optionalUser.isPresent()) {
            User user = optionalUser.get(); // obtenemos el usuario
            // creamos una nueva movie
            Movie movietoSave = new Movie(movie.getTitle(), movie.getAuthor(), movie.getCountry(), movie.getRating(), user);
            //guardamos
            movieRepository.save(movietoSave);

            // Actualizar la lista de películas del usuario
            user.getMoviesList().add(movietoSave);
            userRepository.save(user);



             URI newMovieLocation = ucb
                    .path("/movies/{id}")
                    .buildAndExpand(movietoSave.getId())
                    .toUri();

             return  ResponseEntity.created(newMovieLocation).build();

        }

         return ResponseEntity.notFound().build();



    }



}
