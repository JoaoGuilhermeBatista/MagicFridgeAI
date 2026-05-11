package dev.java10x.magicfridgeai.Service;
import dev.java10x.magicfridgeai.Model.FoodItemModel;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    private final WebClient webClient;
    private String apiKey = System.getenv("OPENAI_API_KEY");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItemModel> foodItemModels) {
        String alimentos = foodItemModels.stream()

                .map(item -> String.format(
                        "%s (%s) - Quantidade: %d, Validade: %s",

                        item.getNome(),
                        item.getCategoria(),
                        item.getQuantidade(),
                        item.getValidade()
                ))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu banco de dados faça uma receita com os seguintes itens:\n " + alimentos;

        Map<String,Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "input", List.of(
                        Map.of(
                                "role", "developer",
                                "content", "Você é um chef especialista"
                        ),
                        Map.of(
                                "role","user",
                                "content", prompt
                        )
                )
        );
        return webClient.post()
                .uri("/responses")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var output = (List<Map<String, Object>>) response.get("output");
                    if (output != null && !output.isEmpty()) {
                        Map<String, Object> firstOutput = output.get(0);
                        var content = (List<Map<String, Object>>) firstOutput.get("content");
                        if (content != null && !content.isEmpty()) {
                            Map<String, Object> firstContent = content.get(0);
                            return firstContent.get("text").toString();
                        }
                    }
                    return "Nenhuma receita foi gerada.";
                });
    }

}


/*
curl https://api.openai.com/v1/responses \
        -H "Content-Type: application/json" \
        -H "Authorization: Bearer $OPENAI_API_KEY" \
        -d '{
        "model": "gpt-5.5",
        "input": "Write a short bedtime story about a unicorn."
        }'
*/
