package br.com.media.screenmatch.service;

import br.com.media.screenmatch.config.DataConfig;
import br.com.media.screenmatch.models.Serie;
import br.com.media.screenmatch.models.SerieData;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SerieService {
	private ApiConsumption consumption = new ApiConsumption();
	private DataConverter converter = new DataConverter();
	private DataConfig dataConfig = new DataConfig();
	private final String ADDRESS = "https://www.omdbapi.com/?t=";
	private final String API_KEY = dataConfig.getConfig();
	private List<SerieData> seriesData = new ArrayList<>();
	SerieData data;


	public void searchWebSerie(String serieName) {
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		data = converter.getData(json, SerieData.class);
		System.out.println(data.title() + " - " + data.year() + ", avaliação média no IMDB: " + data.imdbRating() + ", contém " + data.totalSeasons() + " temporadas.");
		seriesData.add(data);
	}

	public void listSeries() {
		List<Serie> series = new ArrayList<>();
series = seriesData.stream()
		.map(d -> new Serie(d))
		.collect(Collectors.toList());
series.stream()
		.sorted(Comparator.comparing(Serie::getGenre))
		.forEach(System.out::println);
	}



}