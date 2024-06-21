package br.com.media.screenmatch.models.repository;

import br.com.media.screenmatch.models.Category;
import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.Serie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTitleContainingIgnoreCase(String title);

	List<Serie> findByYearContaining(String year);

	List<Serie> findTop5ByOrderByImdbRatingDesc();

	List<Serie> findByGenre(Category category);

	@Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
	List<Episode> topEpisodesBySerie(Serie serie);

	@Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR(e.releaseDate) >= :releaseYear")
	List<Episode> episodesBySerieAndYear(Serie serie, int releaseYear);

	List<Serie> findTop5ByOrderByEpisodesReleaseDateDesc();
}
