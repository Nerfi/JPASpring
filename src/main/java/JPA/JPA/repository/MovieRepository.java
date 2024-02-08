package JPA.JPA.repository;

import JPA.JPA.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;




public interface MovieRepository extends JpaRepository<Movie, Long> {
}
