package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import com.gabriel.magicfridgeai.MagicFridgeAI.Service.FoodItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")

public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public FoodItem cadastrar(@RequestBody FoodItem foodItem){
        return foodItemService.salvar(foodItem);
    }

    @GetMapping
    public List<FoodItem> listarTodos(){
        return foodItemService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<FoodItem> buscarPorId(@PathVariable Long id) {
        return foodItemService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FoodItem atualizar(@PathVariable Long id, @RequestBody FoodItem foodItem){
        return foodItemService.atualizar(id, foodItem);
        }
    @DeleteMapping("/{id}")
    public boolean excluir(@PathVariable Long id){
        return foodItemService.excluir(id);
        }


}
