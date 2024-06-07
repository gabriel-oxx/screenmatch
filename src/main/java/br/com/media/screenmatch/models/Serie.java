package br.com.media.screenmatch.models;

//import br.com.media.screenmatch.service.TranslateService;

import jakarta.persistence.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Entity
@Table(name = "series")
public class Serie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String title;
	private String year;
	private Integer totalSeasons;
	private double imdbRating;
	@Enumerated(EnumType.STRING)
	private Category genre;
	private String actores;
	private String plot;
	private String post;
	@OneToMany(mappedBy = "serie")
	private List<Episode> episodes = new ArrayList<>();

	public Serie() {
	}

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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Serie(SerieData serieData) throws URISyntaxException {
		this.actores = serieData.actores();
		this.plot = serieData.plot();
		this.post = serieData.post();
		this.title = serieData.title();
		this.totalSeasons = serieData.totalSeasons();
		this.year = serieData.year();
		this.imdbRating = OptionalDouble.of(Double.valueOf(serieData.imdbRating())).orElse(0);

		try {
			String genreString = serieData.genre().split(",")[0].trim();
			this.genre = Category.valueOf(genreString.toUpperCase());
		} catch (IllegalArgumentException error) {
			System.err.println("Gênero não encontrado" + error);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	@Override
	public String toString() {
		return "Título: " + title +
				"(" + year + ")" +
				"," + "total de temporadas: " + totalSeasons +
				", média de avaliações no IMDB: " + imdbRating +
				", gênero: " + genre +
				", atores: " + actores +
				",\n sinopse: " + plot +
				",\n post: " + post
				;
	}


}
