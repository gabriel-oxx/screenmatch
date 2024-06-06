package br.com.media.screenmatch.models.repository;

import br.com.media.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SerieRepository extends JpaRepository<Serie, Long> {
}
