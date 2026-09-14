package com.gabriel.magicfridgeai.MagicFridgeAI.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.JsonNode;

@Service

public class ChatGptService {

    private final WebClient webClient;
    private final String model;

    public ChatGptService(
            WebClient webClient,
            @Value("${openai.model}") String model) {

        this.webClient = webClient;
        this.model = model;
    }
    private String extrairTexto(JsonNode resposta) {
        for (JsonNode item : resposta.path("output")) {
            if ("message".equals(item.path("type").asText())) {
                for (JsonNode content : item.path("content")) {
                    if ("output_text".equals(content.path("type").asText())) {
                        return content.path("text").asText();
                    }
                }
            }
        }
        return "A IA não retornou uma resposta em texto.";
    }

    public Mono<String> gerarResposta(String prompt){

        Map<String, Object> body = Map.of("model", model, "input", prompt);
        return webClient.post()
                .uri("/responses")
                .bodyValue(body)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("Erro retornado pela OpenAI:");
                                    System.out.println(errorBody);

                                    return Mono.error(
                                            new RuntimeException("Erro na chamada da OpenAI")
                                    );
                                })
                )
                .bodyToMono(JsonNode.class)
                .map(this::extrairTexto);
    }

}
