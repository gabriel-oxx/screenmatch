package br.com.media.screenmatch.controller;

import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
	@Autowired
	private SerieRepository repository;

	@GetMapping("/series")
	public List<SerieDto> getSeries() {
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
	}


}
