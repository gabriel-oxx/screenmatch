package br.com.media.screenmatch.service;

import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
	@Autowired
	private SerieRepository repository;

	public List<SerieDto> getAllSeries() {
		/*
		return repository.findAll()
				.stream()
				.map(s -> new SerieDto(
						s.getId(),
						s.getTitle(),
						s.getYear(),
						s.getGenre(),
						s.getTotalSeasons(),
						s.getPlot(),
						s.getActores(),
						s.getImdbRating(),
						s.getPost()
				))
				.collect(Collectors.toList());

		 */

		return dataConverter(repository.findAll());
	}

	public List<SerieDto> getTop5Series() {
		return dataConverter(repository.findTop5ByOrderByImdbRatingDesc());
	}

	private List<SerieDto> dataConverter(List<Serie> series) {
		return series.stream()
				.map(s -> new SerieDto(
						s.getId(),
						s.getTitle(),
						s.getYear(),
						s.getGenre(),
						s.getTotalSeasons(),
						s.getPlot(),
						s.getActores(),
						s.getImdbRating(),
						s.getPost()
				))
				.collect(Collectors.toList());
	}

	public List<SerieDto> getReleases() {
		return dataConverter(repository.findTop5ByOrderByEpisodesReleaseDateDesc());
	}


	public SerieDto getById(Long id) {
		Optional<Serie> serie = repository.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return new SerieDto(
					s.getId(),
					s.getTitle(),
					s.getYear(),
					s.getGenre(),
					s.getTotalSeasons(),
					s.getPlot(),
					s.getActores(),
					s.getImdbRating(),
					s.getPost()
			);
		}
		return null;
	}


}

