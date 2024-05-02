package br.com.media.screenmatch.service;

public interface DataConverterI {
<T> T getData(String json, Class<T> dataClass);
}
