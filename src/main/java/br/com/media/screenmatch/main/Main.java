package br.com.media.screenmatch.main;

import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.SeasonData;
import br.com.media.screenmatch.service.*;

import java.time.LocalDate;
import java.util.*;

public class Main {
	private final Scanner input = new Scanner(System.in);

	public void displayMenu() {
		SerieService serieService = new SerieService();
		EpisodeService episodeService = new EpisodeService();
		RatingService ratingService = new RatingService();
		DateService dateService = new DateService();

		System.out.println("Insira o nome da série");
		String serieName = input.nextLine();

		List<SeasonData> seasons = serieService.getSeasons(serieName);
		seasons.forEach(System.out::println);
		List<Episode> episodes = episodeService.createEpisodeList(seasons);
		episodes.forEach(System.out::println);
		System.out.println("Qual episódio você gostaria de buscar?");
		var partOfTitle = input.nextLine();
		Optional<Episode> searchedEpisode = episodeService.searchEpisode(episodes, partOfTitle);

		if (searchedEpisode.isPresent()) {
			System.out.println("Episódio encontrado!");
			System.out.println("Temporada: " + searchedEpisode.get().getSeason());
		} else {
			System.out.println("Não foi possível encontrar esse episódio.");
		}

		Map<Integer, Double> ratingsBySeason = ratingService.getRatingsBySeason(episodes);

		for (Map.Entry<Integer, Double> entry : ratingsBySeason.entrySet()) {
			System.out.printf("%d: %.2f\n", entry.getKey(), entry.getValue());
		}

		DoubleSummaryStatistics estatistics = ratingService.getRatingStatistics(episodes);
		System.out.println("Média: " + estatistics.getAverage());
		System.out.println("Menor avaliação: " + estatistics.getMin());
		System.out.println("Maior avaliação:" + estatistics.getMax());
		System.out.println("Total de episódios avaliados: " + estatistics.getSum());
		System.out.println("A partir de que ano você deseja ver os episódios?");
		var year = input.nextInt();
		input.nextLine();
		LocalDate dateSearch = LocalDate.of(year, 1, 1);
		dateService.filterEpisodesByReleaseDate(episodes, dateSearch);
	}

}
