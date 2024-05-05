package br.com.media.screenmatch.main;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.EpisodeData;
import br.com.media.screenmatch.models.SeasonData;
import br.com.media.screenmatch.models.SerieData;
import br.com.media.screenmatch.service.ApiConsumption;
import br.com.media.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	private Scanner input = new Scanner(System.in);
	private DataConfig dataConfig = new DataConfig();
	private ApiConsumption consumption = new ApiConsumption();
	private DataConverter converter = new DataConverter();
	private final String ADDRESS = "https://www.omdbapi.com/?t=";
	private final String API_KEY = dataConfig.getConfig();

	public void displayMenu() {
		System.out.println("Insira o nome da série");
		String serieName = input.nextLine();
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		SerieData serie = converter.getData(json, SerieData.class);
		System.out.println(serie);
		List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i < serie.totalSeasons(); i++) {
			json = consumption.getData(ADDRESS + serieName.replace(" ", "+") + "&season=" + i + "&apikey=" + API_KEY);
			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

		seasons.forEach(System.out::println);
//exibindo os títulos dos episódios
		//seasons.forEach(s -> s.episodes().forEach(e -> System.out.println(e.title())));
		List<EpisodeData> episodesList = seasons.stream() //Criando um fluxo de dados com os dados de uma temporada
				.flatMap(s -> s.episodes().stream()) //Criando um fluxo de dados com os dados de um episódio
				.collect(Collectors.toList()); //Agrupando todos os episódios em uma lista única

		System.out.println("Top 5 episódios:\n");
		episodesList.stream()
				.filter(e -> !e.rating().equalsIgnoreCase("n/a"))
				.sorted(
						Comparator.comparing(EpisodeData::rating).reversed()
				)
				.limit(5)
				.forEach(System.out::println);

		List<Episode> episodes = seasons.stream()
				.flatMap(s -> s.episodes()
						.stream()
						.map(d -> new Episode(s.number(), d))
				)
				.collect(Collectors.toList());

		episodes.forEach(System.out::println);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("A partir de que ano você deseja ver os episódios?");
		var year = input.nextInt();
		input.nextLine();
		LocalDate dateSearch = LocalDate.of(year, 1, 1);
		episodes.stream().filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(dateSearch))
				.forEach(e -> System.out.println(
						"Temporada: " + e.getSeason()
						+ " episódio: " + e.getTitle()
						+ " data de lançamento: " + e.getReleaseDate().format(formatter)
				));
	}


}

