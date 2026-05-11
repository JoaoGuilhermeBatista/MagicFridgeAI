package dev.java10x.magicfridgeai.Controller;


import dev.java10x.magicfridgeai.Model.FoodItemModel;
import dev.java10x.magicfridgeai.Service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    //GET
    @GetMapping
    public ResponseEntity<List<FoodItemModel>> listar() {
        List<FoodItemModel> lista = foodItemService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
   public ResponseEntity<FoodItemModel> listarPorId(Long id) {

    Optional<FoodItemModel> foodItem = foodItemService.listarporId(id);

    if (foodItem.isPresent()) {
        return ResponseEntity.ok(foodItem.get());
    } else  {
        return ResponseEntity.notFound().build();
    }
   }

    //POST
    @PostMapping
    public ResponseEntity<FoodItemModel> criar (@RequestBody FoodItemModel fooditem) {
        FoodItemModel salvo = foodItemService.salvar(fooditem);
        return ResponseEntity.ok(salvo);
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemModel> atualizarComida(@PathVariable Long id, @RequestBody FoodItemModel foodItemAtualizada) {

        Optional<FoodItemModel> food = foodItemService.atualizarComida(id, foodItemAtualizada);

        if(food.isPresent()) {
            return ResponseEntity.ok(food.get());
        }

        return ResponseEntity.notFound().build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComidaPorId (@PathVariable Long id) {
        foodItemService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
