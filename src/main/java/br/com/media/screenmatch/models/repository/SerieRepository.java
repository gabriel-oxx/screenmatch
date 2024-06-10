package br.com.media.screenmatch.models.repository;

import br.com.media.screenmatch.models.Category;
import br.com.media.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTitleContainingIgnoreCase(String title);

	List<Serie> findByYearContaining(String year);

	List<Serie> findTop5ByOrderByImdbRatingDesc();

	List<Serie> findByGenre(Category category);

}
