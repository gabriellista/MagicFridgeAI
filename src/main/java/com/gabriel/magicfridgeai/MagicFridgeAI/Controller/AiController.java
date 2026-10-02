package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Service.ReceitaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final ReceitaService receitaService;

    public AiController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping("/receita")
    public Mono<String> gerarReceita() {
        return receitaService.gerarReceita();
    }
}
