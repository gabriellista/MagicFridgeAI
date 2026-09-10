package com.gabriel.magicfridgeai.MagicFridgeAI.Service;

import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import com.gabriel.magicfridgeai.MagicFridgeAI.Repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public FoodItem salvar(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public List<FoodItem> listarTodos() {
        return foodItemRepository.findAll();

    }

    public Optional<FoodItem> buscarPorId(Long id) {
        return foodItemRepository.findById(id);
    }

    public FoodItem atualizar(Long id ,FoodItem foodItem) {
       if (foodItemRepository.existsById(id)){
           foodItem.setId(id);
           return foodItemRepository.save(foodItem);
       }
       return null;
    }

    public boolean excluir(Long id){
        if (foodItemRepository.existsById(id)) {
            foodItemRepository.deleteById(id);
            return  true;
    }
        return false;
    }
}
