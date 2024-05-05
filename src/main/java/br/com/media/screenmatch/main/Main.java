package br.com.media.screenmatch.main;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.SerieData;
import br.com.media.screenmatch.service.ApiConsumption;
import br.com.media.screenmatch.service.DataConverter;

import java.util.Scanner;

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
String json =  consumption.getData(url);
		SerieData serie = converter.getData(json, SerieData.class);
		System.out.println(serie);
	}

}
