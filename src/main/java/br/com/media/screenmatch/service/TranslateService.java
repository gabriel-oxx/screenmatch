package br.com.media.screenmatch.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;

public class TranslateService {

	public  static  String translate(String text){
		ChatLanguageModel model = VertexAiGeminiChatModel.builder()
				.project("screenmatch-423304")
				.location("us-central1")
				.modelName("gemini-1.5-flash-001")
				.build();

		return model.generate("Traduza o texto a seguir, mas apenas se ele não já estiver em português. Não acrescente nenhuma informação, apenas traduza o texto. Texto: " +text);
	}
}


