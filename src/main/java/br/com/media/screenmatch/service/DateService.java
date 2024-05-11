package br.com.media.screenmatch.service;

import br.com.media.screenmatch.models.Episode;

import  java.util.List;
import  java.time.LocalDate;
import  java.time.format.DateTimeFormatter;

public class DateService {
	public void filterEpisodesByReleaseDate(List<Episode> episodes, LocalDate dateSearch) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		episodes.stream().filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(dateSearch))
				.forEach(e -> System.out.println(
						"Temporada: " + e.getSeason()
								+ " episódio: " + e.getTitle()
								+ " data de lançamento: " + e.getReleaseDate().format(formatter)
				));
	}


}
