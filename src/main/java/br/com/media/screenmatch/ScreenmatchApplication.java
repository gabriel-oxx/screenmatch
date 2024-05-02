package br.com.media.screenmatch;

import br.com.media.screenmatch.models.DataSerie;
import br.com.media.screenmatch.service.ApiConsumption;
import br.com.media.screenmatch.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//implementação do método run da interface CommandLineRunner
	@Override
	public void run(String... args) throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream("config.properties"));
		String apiKey = properties.getProperty("apiKey");
		String name = "The+Flash";
		String url = "https://www.omdbapi.com/?t=" + name + "&apikey=" + apiKey;
		var apiConsumption = new ApiConsumption();
		var json = apiConsumption.getData(url);
		System.out.println(json);
		DataConverter converter = new DataConverter();
		DataSerie data = converter.getData(json, DataSerie.class);
		System.out.println(data);
	}

}
