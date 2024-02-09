package JPA.JPA.controllers;

import JPA.JPA.models.Movie;
import JPA.JPA.models.User;
import JPA.JPA.repository.MovieRepository;
import JPA.JPA.repository.UserRepository;
import JPA.JPA.security.jwt.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
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
            //obtenemos el nombre del usuario que esta creando esta resource y lo añadimos a la movie
            movietoSave.setOwner(user.getUsername());

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

         return ResponseEntity.notFound().build(); // not sure if this is correct

    }

    //update
    // NO FUNCIONA DE MOMENTO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Void> updateMovie(@PathVariable Long id, @RequestBody Movie movie, Principal principal) {

        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        Optional<Movie> movieToUpdateTest = movieRepository.findById(id);

        if(optionalUser.isPresent() && movieToUpdateTest.isPresent()) {
            Movie newMovieUpdated = new Movie(movie.getTitle(), movie.getAuthor(), movie.getCountry(), movie.getRating(), optionalUser.get());
            //adding the owner
            newMovieUpdated.setOwner(principal.getName());
            movieRepository.save(newMovieUpdated);
            return ResponseEntity.noContent().build();
        }

        //System.out.println(movieToUpdateTest + " the movie to updated");

        //Movie movieToUpdate = movieRepository.findByIdAndOwner(id, principal.getName());
       // System.out.println(movieToUpdate + " movie to update");

        // si tenemos movie procedemos a actualizar

//        if(movieToUpdate != null && optionalUser.isPresent()) {
//            // utilizamos movieToUpdate.getId() para saber que id es el que queremos hacer updated, luego añadimos los valores que nos vienen por request
//            Movie movieUpdated = new Movie(movie.getTitle(), movie.getAuthor(), movie.getCountry(), movie.getRating(), optionalUser.get());
//            movieUpdated.setOwner(principal.getName());
//            //devolvemos no content ya que al hacer PUt no hay nada que devolver
//            movieRepository.save(movieUpdated);
//            return ResponseEntity.noContent().build();
//        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id, Principal principal) {
        // further reading to create GOOD API REST https://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api
        //añadimos principal solo para saber si este es nuestra movie y poder eliminarla
        if(movieRepository.existsByIdAndOwner(id, principal.getName())) {
            movieRepository.deleteById(id);

            return ResponseEntity.noContent().build();
        }

        return  ResponseEntity.notFound().build();
    }



}
