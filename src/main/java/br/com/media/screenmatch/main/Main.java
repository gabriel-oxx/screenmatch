package br.com.media.screenmatch.main;

import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.SeasonData;
import br.com.media.screenmatch.models.SerieData;
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
			option = input.nextInt();
			input.nextLine();

			switch (option) {
				case 0:
					System.out.println("Saindo...");
					break;
				case 1:
					System.out.println("Insira o nome da série que você deseja pesquisar");
					search = input.nextLine();
					serieService.searchWebSerie(search);
					break;
				case 2:
					System.out.println("Insira o nome do episódio que você deseja pesquisar");
					search = input.nextLine();
					break;
				case 3:
					serieService.listSeries();
					break;
				default:
					System.out.println("Opção inválida");
					break;
			}
		}

	}


}
