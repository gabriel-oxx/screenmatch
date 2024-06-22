package br.com.media.screenmatch.service;

import br.com.media.screenmatch.dto.EpisodeDto;
import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.models.Category;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return dataConverter(repository.latestReleases());
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


	public List<EpisodeDto> getAllSeasons(Long id) {
		Optional<Serie> serie = repository.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return s.getEpisodes()
					.stream()
					.map(
							e -> new EpisodeDto(
									e.getTitle(),
									e.getNumber(),
									e.getSeason()
							)
					)
					.collect(Collectors.toList());
		}
		return  null;
	}


	public EpisodeDto getSeasonByNumber(Long id, Long number) {
return  repository.getEpisodesBySeason(id, number)
		.stream()
		.map(
				e -> new EpisodeDto(
						e.getTitle(),
						e.getNumber(),
						e.getSeason()
				)
		)
		.collect(Collectors.toList());
	}


	public List<SerieDto> getSerieByGenre(String genre) {
		Category category;
		return  dataConverter(repository.findByGenre(category));
	}


}

