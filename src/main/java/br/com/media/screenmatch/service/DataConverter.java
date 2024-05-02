package br.com.media.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements DataConverterI {
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public <T> T getData(String json, Class<T> dataClass) {
		try {
			return mapper.readValue(json, dataClass);
		} catch (JsonProcessingException error) {
			throw new RuntimeException(error);
		}
	}


}
