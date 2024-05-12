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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getTotalSeasons() {
		return totalSeasons;
	}

	public void setTotalSeasons(Integer totalSeasons) {
		this.totalSeasons = totalSeasons;
	}

	public double getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(double imdbRating) {
		this.imdbRating = imdbRating;
	}

	public Category getGenre() {
		return genre;
	}

	public void setGenre(Category genre) {
		this.genre = genre;
	}

	public String getActores() {
		return actores;
	}

	public void setActores(String actores) {
		this.actores = actores;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public StringgetPost() {
		return post;
	}

	public void stPost(String post) {
		this.post = pst;
	}

	public Serie(erieData serieData) {
		this.actores serieData.actores();
		this.genre = ategory.valueOf(serieData.genre().split(",")[0].trim());
		this.plot = srieData.plot();
		this.post = srieData.post();
		this.title = erieData.title();
		this.totalSeaons = serieData.totalSeasons();
		this.year = srieData.year();

		this.imdbRatig = OptionalDouble.of(Double.valueOf(serieData.imdbRating())).orElse(0);
	}

	@Override
	public String toString() {
		return "Título: " + title +
				+"(" + year + ")" +
				"," + "total de temporadas: " + totalSeasons +
				", média de avaliações no IMDB: " + imdbRating +
				", gênero: " + genre +
				", atores: " + actores +
				",\n sinopse: " + plot +
				",\n post: " + post
				;
	}


}
