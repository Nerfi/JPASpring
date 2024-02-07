package JPA.JPA.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")

//@RequestMapping("/movies")

//@CrossOrigin(origins = "http://localhost:8080")
public class MoviesController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    private String test(){
        return "this is the test for movies";
    }

    @GetMapping("/all/2")
    private String test2(){
        Authentication testController2 = SecurityContextHolder.getContext().getAuthentication();
        return "this is the test for movies";
    }
}
