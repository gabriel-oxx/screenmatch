package br.com.media.screenmatch;

import br.com.media.screenmatch.main.Main;
import br.com.media.screenmatch.models.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//implementação do método run da interface CommandLineRunner
	@Override
	public void run(String... args) throws URISyntaxException {
		Main main = new Main(repository);
		main.displayMenu();
	}


}
