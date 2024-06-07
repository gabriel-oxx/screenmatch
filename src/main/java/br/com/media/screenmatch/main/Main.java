package br.com.media.screenmatch.main;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.SeasonData;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.SerieData;
import br.com.media.screenmatch.service.*;
import br.com.media.screenmatch.models.repository.SerieRepository;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
	private final Scanner input = new Scanner(System.in);
	ApiConsumption consumption = new ApiConsumption();
	DataConverter converter = new DataConverter();
	DataConfig config = new DataConfig();
	SerieData data;
	private SerieRepository repository;
	private List<Serie> series = new ArrayList<>();

	public Main(SerieRepository repository) {
		this.repository = repository;
	}

	public void displayMenu() {

		String menu = """
				Escolha uma das opções:
				1 - pesquisar séries
				2 - Verificar episódios de uma série
				3 - listar séries 
				0 - sair
				""";
		String search;
		int option = -1;

		while (option != 0) {
			System.out.println(menu);
			try {

				option = input.nextInt();
				input.nextLine();

				switch (option) {
					case 0:
						System.out.println("Saindo...");
						break;
					case 1:
						System.out.println("Insira o nome da série que você deseja pesquisar");
						try {
							search = input.nextLine();
							searchWebSerie(search);
						} catch (InputMismatchException error) {
							System.err.println("O valor que você inseriu não é válido aqui." + error.getMessage());
						}
						break;
					case 2:
						listSeriesEpisode();
						break;
					case 3:
						listSeries();
						break;
					default:
						System.out.println("Opção inválida");
						break;
				}
			} catch (InputMismatchException error) {
				System.err.println("O valor que você inseriu não é válido aqui." + error.getMessage());
			}
		}

	}


	private void searchWebSerie(String serieName) {
		String API_KEY = System.getenv("OMDB_API_KEY");
		String ADDRESS = "https://www.omdbapi.com/?t=";
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		data = converter.getData(json, SerieData.class);
		System.out.println(data.title() + " - " + data.year() + ", avaliação média no IMDB: " + data.imdbRating() + ", contém " + data.totalSeasons() + " temporadas.");
		Serie serie = null;
		try {
			serie = new Serie(data);
			repository.save(serie);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public void listSeries() {
		series = repository.findAll();
		try {
			List<Serie> seriesWithGenre = series.stream()
					.filter(s -> s.getGenre() != null)
					.sorted(Comparator.comparing(Serie::getGenre))
					.collect(Collectors.toList());
			List<Serie> seriesWithoutGenre = series.stream()
					.filter(s -> s.getGenre() == null)
					.collect(Collectors.toList());
			List<Serie> sortedSeries = new ArrayList<>(seriesWithGenre);
			sortedSeries.addAll(seriesWithoutGenre);
			sortedSeries.forEach(System.out::println);
		} catch (NullPointerException error) {
			System.err.println("Temos uma exceção aqui: " + error.getMessage());
		}

	}

	private void listSeriesEpisode() {
		System.out.println("Essa é a lista de séries disponíveis no momento:");
		listSeries();
		System.out.println("Digite o nome de uma das séries dessa lista e eu exibirei os episódios dela pra você");
		String search = input.nextLine();
		Optional<Serie> serie = series.stream()
				.filter(s -> s.getTitle().toLowerCase().contains(search.toLowerCase()))
				.findFirst();
		List<SeasonData> seasons = new ArrayList<>();

		if (serie.isPresent()) {
			var seriesFound = serie.get();

			for (int i = 1; i <= seriesFound.getTotalSeasons(); i++) {

				String API_KEY = System.getenv("OMDB_API_KEY");
				String ADDRESS = "https://www.omdbapi.com/?t=";
				String url = ADDRESS + seriesFound.getTitle().replace(" ", "+") + "&season=" + i + "&apikey=" + API_KEY;
				var json = consumption.getData(url);
				SeasonData season = converter.getData(json, SeasonData.class);
				seasons.add(season);
			}


			List<Episode> episodes = seasons.stream()
					.flatMap(d -> d.episodes()
							.stream()
							.map(e -> new Episode(d.number(), e))
					)
					.collect(Collectors.toList());
			episodes.forEach(System.out::println);
			seriesFound.setEpisodes(episodes);
			repository.save(seriesFound);
		} else {
			System.out.println("Não foi possível encontrar essa série por aqui");
		}

	}


}

