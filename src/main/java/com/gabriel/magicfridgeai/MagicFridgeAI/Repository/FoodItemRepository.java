package com.gabriel.magicfridgeai.MagicFridgeAI.Repository;


import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
