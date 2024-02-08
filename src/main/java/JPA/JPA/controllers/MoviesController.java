package JPA.JPA.controllers;

import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies")

public class MoviesController {

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    private String test(){
        return "this is the test for movies";
    }


}
