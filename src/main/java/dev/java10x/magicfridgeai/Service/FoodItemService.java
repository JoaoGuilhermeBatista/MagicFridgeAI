package dev.java10x.magicfridgeai.Service;
import dev.java10x.magicfridgeai.Model.FoodItemModel;
import dev.java10x.magicfridgeai.Repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {
    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    public FoodItemModel salvar (FoodItemModel fooditem) {
        return repository.save(fooditem);
    }


    public List<FoodItemModel> listar() {
        return repository.findAll();
    }

    public Optional<FoodItemModel> listarporId (Long id) {
        return repository.findById(id);
    }

    public Optional<FoodItemModel> atualizarComida(Long id, FoodItemModel foodItemAtualizada) {

        if(repository.existsById(id)) {

            foodItemAtualizada.setId(id);

            return Optional.of(
                    repository.save(foodItemAtualizada)
            );
        }

        return Optional.empty();
    }

    public void deletarPorId (Long id) {
        repository.deleteById(id);
    }
}
