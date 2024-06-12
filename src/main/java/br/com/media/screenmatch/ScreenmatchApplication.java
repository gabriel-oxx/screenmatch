package br.com.media.screenmatch;

import br.com.media.screenmatch.main.Main;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;

@SpringBootApplication
public class ScreenmatchApplication{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}


}
