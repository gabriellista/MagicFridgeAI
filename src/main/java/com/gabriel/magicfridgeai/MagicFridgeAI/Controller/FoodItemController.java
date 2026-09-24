package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import com.gabriel.magicfridgeai.MagicFridgeAI.Service.FoodItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/food")

public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @PostMapping
    public ResponseEntity<FoodItem> cadastrar(@Valid @RequestBody FoodItem foodItem){
        FoodItem alimentoSalvo = foodItemService.salvar(foodItem);
        return ResponseEntity.status(201).body(alimentoSalvo);
    }

    @GetMapping
    public List<FoodItem> listarTodos(){
        return foodItemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> buscarPorId(@PathVariable Long id) {
        Optional<FoodItem> foodItem = foodItemService.buscarPorId(id);
        if (foodItem.isPresent()) {
            return ResponseEntity.ok(foodItem.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> atualizar(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
        FoodItem alimentoAtualizado = foodItemService.atualizar(id, foodItem);
        if (alimentoAtualizado != null) {
            return ResponseEntity.ok(alimentoAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean excluido = foodItemService.excluir(id);
        if (excluido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
