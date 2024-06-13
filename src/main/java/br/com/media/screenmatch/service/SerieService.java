package br.com.media.screenmatch.service;

import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
	@Autowired
	private SerieRepository repository;

	public List<SerieDto> getAllSeries(){
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
