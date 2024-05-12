package br.com.media.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(
		@JsonAlias("Title") String title,
		@JsonAlias("Year") String year,
		Integer totalSeasons,
		String imdbRating,
		@JsonAlias("Genre") String genre,
		@JsonAlias("Actors") String actores,
		@JsonAlias("Plot") String plot,
		@JsonAlias("Poster") String post
) {
}
