package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Service.ChatGptService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai")

public class AiController {

    private final ChatGptService chatGptService;

    public AiController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/test")
    public Mono<String> testar(@RequestParam String prompt){
        return chatGptService.gerarResposta(prompt) ;
    }

}
