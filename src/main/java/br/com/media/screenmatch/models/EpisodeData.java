package br.com.media.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(
		@JsonAlias("Title") String title,
		@JsonAlias("Episode") Integer number,
		@JsonAlias("imdbRating") String rating,
		@JsonAlias("Released") String releaseDate,
		@JsonAlias("Genre") String genre) {
}

