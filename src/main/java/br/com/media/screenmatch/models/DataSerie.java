package br.com.media.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSerie(String Title, String Year, Integer totalSeasons, String imdbRating) {
}
