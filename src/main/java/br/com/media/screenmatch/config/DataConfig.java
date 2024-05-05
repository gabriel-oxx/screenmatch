package br.com.media.screenmatch.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataConfig {
	private String apiKey;
	//br/com/media/screenmatch/config/

	public String getConfig( ){
		Properties properties = new Properties();
		try (InputStream input = new FileInputStream("config.properties")) {
			if (input == null) {
				throw new RuntimeException("Não foi possível encontrar o arquivo de configurações");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		 apiKey = properties.getProperty("apiKey");
		return  apiKey;
	}
}
