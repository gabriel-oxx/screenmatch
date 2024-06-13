package br.com.media.screenmatch.controller;

import br.com.media.screenmatch.dto.SerieDto;
import br.com.media.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {
	@Autowired
	private SerieService service;

	@GetMapping("/series")
	public List<SerieDto> getSeries() {
		return service.getAllSeries();
	}


}
