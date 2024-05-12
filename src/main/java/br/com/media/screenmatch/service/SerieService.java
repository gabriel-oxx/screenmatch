package br.com.media.screenmatch.service;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.SeasonData;
import br.com.media.screenmatch.models.SerieData;

import java.util.List;
import java.util.ArrayList;

public class SerieService {
	private ApiConsumption consumption = new ApiConsumption();
	private DataConverter converter = new DataConverter();
	private DataConfig dataConfig = new DataConfig();
	private final String ADDRESS = "https://www.omdbapi.com/?t=";
	private final String API_KEY = dataConfig.getConfig();
	private List<SerieData> series = new ArrayList<>();
	SerieData data;

	public void searchWebSerie(String serieName) {
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		data = converter.getData(json, SerieData.class);
		System.out.println(data.title() + " - " + data.year() + ", avaliação média no IMDB: " + data.imdbRating() + ", contém " + data.totalSeasons() + " temporadas.");
		series.add(data);
	}

	public void listSeries() {
		series.forEach(System.out::println);
	}



}