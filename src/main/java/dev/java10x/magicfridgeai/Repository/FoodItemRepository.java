package dev.java10x.magicfridgeai.Repository;

import dev.java10x.magicfridgeai.Model.FoodItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItemModel, Long> {
}
