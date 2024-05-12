package br.com.media.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {
	private String title;
	private String year;
	private Integer totalSeasons;
	private double imdbRating;
	private Category genre;
	private String actores;
	private String plot;
	private String post;

	public Serie(SerieData serieData) {
		this.actores = serieData.actores();
		this.genre = Category.valueOf(serieData.genre().split(",")[0].trim());
		this.plot = serieData.plot();
		this.post = serieData.post();
		this.title = serieData.title();
		this.totalSeasons = serieData.totalSeasons();
		this.year = serieData.year();

		this.imdbRating = OptionalDouble.of(Double.valueOf(serieData.imdbRating())).orElse(0);
	}


}
