package br.com.media.screenmatch.dto;

import br.com.media.screenmatch.models.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDto(
		long id,
		String title,
		String year,
		Category genre,
		Integer totalSeasons,
		String plot,
		String actores,
		double imdbRating,
		String post) {

}
