package br.com.media.screenmatch.service;

import br.com.media.screenmatch.models.EpisodeData;
import br.com.media.screenmatch.models.Episode;
import br.com.media.screenmatch.models.SeasonData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EpisodeService {
	public List<EpisodeData> getEpisodes(List<SeasonData> seasons) {
		return seasons.stream()
				.flatMap(s -> s.episodes().stream())
				.collect(Collectors.toList());
	}

	public List<Episode> createEpisodeList(List<SeasonData> seasons){
		List<Episode> episodes = seasons.stream()
				.flatMap(s -> s.episodes()
						.stream()
						.map(d -> new Episode(s.number(), d))
				)
				.collect(Collectors.toList());
		return  episodes;
	}

	public Optional<Episode> searchEpisode(List<Episode> episodes, String partOfTitle) {
		return episodes.stream()
				.filter(e -> e.getTitle().toUpperCase().contains(partOfTitle.toUpperCase()))
				.findFirst();
	}


}