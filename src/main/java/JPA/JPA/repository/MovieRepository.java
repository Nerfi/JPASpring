package JPA.JPA.repository;

import JPA.JPA.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


// https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html#appendix.query.method.subject
// como recordatorio

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean  existsByTitle(String title);
}
