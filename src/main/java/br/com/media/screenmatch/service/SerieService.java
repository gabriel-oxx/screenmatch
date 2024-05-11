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

	public void searchWebSerie(String serieName){
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		SerieData data = converter.getData(json, SerieData.class);
		series.add(data);
	}

	public List<SeasonData> getSeasons(String serieName) {
		String url = ADDRESS + serieName.replace(" ", "+") + "&apikey=" + API_KEY;
		String json = consumption.getData(url);
		SerieData serie = converter.getData(json, SerieData.class);
		List<SeasonData> seasons = new ArrayList<>();

		for (int i = 1; i <= serie.totalSeasons(); i++) {
			json = consumption.getData(ADDRESS + serieName.replace(" ", "+") + "&season=" + i + "&apikey=" + API_KEY);
			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}

		return seasons;
	}


}