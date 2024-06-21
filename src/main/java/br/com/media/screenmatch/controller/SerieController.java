package br.com.media.screenmatch.controller;

import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.service.SerieService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/series")
public class SerieController {
	@Autowired
	private SerieService service;

	@GetMapping
	public List<SerieDto> getSeries() {
		return service.getAllSeries();
	}

	@GetMapping("/top5")
	public List<SerieDto> getTop5Series() {
		return service.getTop5Series();
	}

	@GetMapping("/releases")
	public List<SerieDto> getReleases() {
		return service.getReleases();
	}

	@GetMapping("/{id}")
	public SerieDto getId(@PathVariable Long id) {
		return service.getById(id);
	}


}

