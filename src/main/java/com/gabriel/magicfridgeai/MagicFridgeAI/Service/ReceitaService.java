package com.gabriel.magicfridgeai.MagicFridgeAI.Service;

import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service

public class ReceitaService {

    FoodItemService foodItemService;
    ChatGptService chatGptService;

    public ReceitaService(FoodItemService foodItemService, ChatGptService chatGptService) {
        this.foodItemService = foodItemService;
        this.chatGptService = chatGptService;
    }

    public Mono<String> gerarReceita() {
        List<FoodItem> alimentos = foodItemService.listarTodos();
        if (alimentos.isEmpty()) {
            return Mono.just("Nenhum alimento cadastrado. Cadastre alimentos antes de gerar uma receita.");
        }

        String nomesAlimentos = "";

        for (FoodItem foodItem : alimentos) {
            nomesAlimentos += foodItem.getNome() + " - quantidade: " + foodItem.getQuantidade() + " " + foodItem.getUnidade() + ", ";
        }
        String prompt = "Crie uma receita usando estes alimentos: "
                + nomesAlimentos
                + ". Use somente os alimentos informados. "
                + "Você pode considerar apenas água, sal e óleo como ingredientes básicos adicionais. "
                + "Responda de forma curta. "
                + "Informe apenas nome da receita, ingredientes e modo de preparo. "
                + " Escreva 'Modo de preparo:' apenas uma vez e depois liste os passos."
                + "Use no máximo 8 linhas";
        return chatGptService.gerarResposta(prompt);
    }

}
