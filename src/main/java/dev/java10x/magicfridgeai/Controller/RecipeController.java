package dev.java10x.magicfridgeai.Controller;

import dev.java10x.magicfridgeai.Model.FoodItemModel;
import dev.java10x.magicfridgeai.Service.ChatGptService;
import dev.java10x.magicfridgeai.Service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class RecipeController {

    private ChatGptService chatGptService;
    private FoodItemService foodItemService;

    public RecipeController(ChatGptService chatGptService, FoodItemService foodItemService) {
        this.chatGptService = chatGptService;
        this.foodItemService = foodItemService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItemModel> foodItemModels = foodItemService.listar();
         return chatGptService.generateRecipe(foodItemModels)
                 .map(recipe -> ResponseEntity.ok(recipe))
                 .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
