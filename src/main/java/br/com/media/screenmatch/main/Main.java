package br.com.media.screenmatch.main;


import br.com.media.screenmatch.models.*;
import br.com.media.screenmatch.service.*;
import br.com.media.screenmatch.models.repository.SerieRepository;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
	private final Scanner input = new Scanner(System.in);
	ApiConsumption consumption = new ApiConsumption();
	DataConverter converter = new DataConverter();
	SerieData data;
	private SerieRepository repository;
	private List<Serie> series = new ArrayList<>();
	private Optional<Serie> searchSerie;

	public Main(SerieRepository repository) {
		this.repository = repository;
	}

	public void displayMenu() {

		String menu = """
				Escolha uma das opções:
				1 - pesquisar séries
				2 - pesquisar série por título
				3 - pesquisar série por ano
				4 - Verificar episódios de uma série
				5 - listar séries
				6 - listar as 5 séries mais bem avaliadas
				7 - pesquisar séries por gênero
				8 - listar os 5 episódios mais bem avaliados de uma série
				9 - buscar episódios por data
				0 - sair
				""";
		String search = "";
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
						searchSerieByTitle();
						break;
					case 3:
						searchSerieByYear();
						break;
					case 4:
						listSeriesEpisode();
						break;
					case 5:
						listSeries();
						break;
					case 6:
						listTop5Series();
						break;
					case 7:
						searchSerieByGenre();
						break;
					case 8:
						listTop5Episodes();
						break;
					case 9:
						searchEpisodeByDate();
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


	private void searchSerieByGenre() {
		System.out.println("Insira a categoria desejada");
		var search = input.nextLine();
		Category category = Category.fromPortuguese(search);
		List<Serie> seriesList = repository.findByGenre(category);

		if (seriesList.size() > 0) {
			System.out.println("Aqui está:");
			seriesList.forEach(s -> System.out.println(s.getTitle()));
		} else
			System.out.println("Nenhuma série com essa categoria foi encontrada");
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
			int lastIndex = sortedSeries.size() - 1;
			for (int i = 0; i < lastIndex; i++) {
				Serie s = sortedSeries.get(i);
				if (i < lastIndex - 1)
					System.out.print(s.getTitle() + ", ");
				else
					System.out.print(s.getTitle() + ".\n");
			}

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

	private void searchSerieByTitle() {
		System.out.println("Essa é a lista de séries disponíveis no momento:");
		listSeries();
		System.out.println("Digite o nome de uma das séries dessa lista");
		String search = input.nextLine();
		searchSerie = repository.findByTitleContainingIgnoreCase(search);

		if (searchSerie.isPresent()) {
			Serie data = searchSerie.get();
			System.out.println("Dados da série:\n");
			System.out.println("Título: " + data.getTitle()
					+ "\nmédia de avaliação: " + data.getImdbRating()
					+ "\ngênero: " + TranslateService.translate(data.getGenre().toString().toLowerCase())
					+ "\nsinopse: " + data.getPlot()
					+ "\natores: " + data.getActores()
					+ "\ntotal de temporadas: " + data.getTotalSeasons() + "."
			);
		} else {
			System.out.println("Não foi possível encontrar essa série");
		}
	}

	void searchSerieByYear() {
		System.out.println("Essa é a lista de séries disponíveis no momento:");
		listSeries();
		System.out.println("Digite um ano, e eu verificarei se háséries lançadas no ano que você escolheu");
		String search = input.nextLine();
		List<Serie> seriesList = repository.findByYearContaining(search);
		if (seriesList.size() > 0) {
			System.out.print("Aqui está:\n");
			seriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + ", ano de lançamento: " + s.getYear()));
		} else
			System.out.println("É, não há nada");
	}

	private void listTop5Series() {
		System.out.println("Essas são as top 5 séries:");
		List<Serie> seriesList = repository.findTop5ByOrderByImdbRatingDesc();
		seriesList.forEach(s -> System.out.println("Título: " + s.getTitle() + " média: " + s.getImdbRating()));
	}


	private void listTop5Episodes() {
		searchSerieByTitle();

		if (searchSerie.isPresent()) {
			Serie serie = searchSerie.get();
			List<Episode> topEpisodes = repository.topEpisodesBySerie(serie);
			topEpisodes.forEach(e -> System.out.println("Título: " + e.getTitle() + " média: " + e.getRating()));
		}

	}

	private void searchEpisodeByDate() {
		searchSerieByTitle();

		if (searchSerie.isPresent()) {
			Serie serie = searchSerie.get();
			System.out.println("Você deseja ver episódios lançados a partir de que ano?");
			var releaseYear = input.nextInt();
			input.nextLine();
			List<Episode> episodes = repository.episodesBySerieAndYear(serie, releaseYear);
			episodes.forEach(System.out::println);
		}
	}


}


