package br.com.media.screenmatch.models;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private Integer number;
	private Integer season;
	private double rating;
	private LocalDate releaseDate;
	@ManyToOne
	private Serie serie;

	public Episode() {
	}

	public Episode(Integer seasonNumber, EpisodeData episodeData) {
		this.season = seasonNumber;
		this.title = episodeData.title();
		this.number = episodeData.number();

		try {
			this.rating = Double.valueOf(episodeData.rating());
		} catch (NumberFormatException error) {
			this.rating = 0.0;
		}

		try {
			this.releaseDate = LocalDate.parse(episodeData.releaseDate());
		} catch (DateTimeParseException error) {
			this.releaseDate = null;
		}
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	@Override
	public String toString() {
		return
				"Temporada " + season +
						", título: '" + title + '\'' +
						", número do episódio: " + number +
						", média de avaliações: " + rating +
						", ano de lançamento: " + releaseDate;
	}


}
