package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import com.gabriel.magicfridgeai.MagicFridgeAI.Service.ChatGptService;
import com.gabriel.magicfridgeai.MagicFridgeAI.Service.FoodItemService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/ai")

public class AiController {

    private final ChatGptService chatGptService;
    private final FoodItemService foodItemService;

    public AiController(ChatGptService chatGptService, FoodItemService foodItemService) {
        this.chatGptService = chatGptService;
        this.foodItemService = foodItemService;
    }

    @PostMapping("/receita")
    public Mono<String> gerarReceita() {
        List<FoodItem> alimentos = foodItemService.listarTodos();
        if (alimentos.isEmpty()){
            return Mono.just("Nenhum alimento cadastrado. Cadastre alimentos antes de gerar uma receita.");
        }

        String nomesAlimentos = "";
        for (FoodItem foodItem : alimentos) {
            nomesAlimentos += foodItem.getNome() + " - quantidade: " + foodItem.getQuantidade() + " " + foodItem.getUnidade() + ", ";

        }

        String prompt = "Crie uma receita usando estes alimentos: "
                + nomesAlimentos
                + ". Responda de forma curta. "
                + "Informe apenas nome da receita, ingredientes e modo de preparo. "
                + "Use no máximo 8 linhas";
        return chatGptService.gerarResposta(prompt);
        }

}
