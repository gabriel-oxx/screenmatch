package br.com.media.screenmatch.main;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.SerieData;
import br.com.media.screenmatch.service.*;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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

	public Main(SerieRepository repository) {
		this.repository = repository;
	}

	public void displayMenu() {

		String menu = """
				Escolha uma das opções:
				1 - pesquisar séries
				2 - pesquisar por episódios
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
						System.out.println("Insira o nome do episódio que você deseja pesquisar");
						search = input.nextLine();
						searchWebSerie(search);
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
		List<Serie> series = repository.findAll();

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


}

