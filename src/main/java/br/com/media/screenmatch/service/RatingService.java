package br.com.media.screenmatch.service;

import br.com.media.screenmatch.models.Episode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.DoubleSummaryStatistics;

	public class RatingService {
	public Map<Integer, Double> getRatingsBySeason(List<Episode> episodes) {
		return episodes.stream()
				.filter(e -> e.getRating() > 0.0)
				.collect(Collectors.groupingBy(Episode::getSeason,
						Collectors.averagingDouble(Episode::getRating)
				));
	}

	public DoubleSummaryStatistics getRatingStatistics(List<Episode> episodes) {
		return episodes.stream()
				.filter(e -> e.getRating() > 0.0)
				.collect(Collectors.summarizingDouble(Episode::getRating));
	}


}