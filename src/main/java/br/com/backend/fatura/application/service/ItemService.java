package br.com.backend.fatura.application.service;

import br.com.backend.fatura.application.model.item.Item;
import br.com.backend.fatura.application.repo.ItemRepository;
import br.com.backend.fatura.application.service.implementation.ItemServiceInterface;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemService implements ItemServiceInterface {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item addItem(Item item) {
        log.info("Item adicionado: " + item);
        return itemRepository.save(item);
    }

    @Override
    public Item deleteItem(ObjectId id) throws ResourceNotFoundException {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        itemRepository.delete(item);
        log.info("Item deletado: " + item);
        return item;
    }

    @Override
    public Item updateItem(ObjectId id, Item newItem) throws ResourceNotFoundException {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existingItem.setDate(newItem.getDate());
        existingItem.setDescription(newItem.getDescription());
        existingItem.setValue(newItem.getValue());

        Item updatedItem = itemRepository.save(existingItem);

        log.info("Item atualizado de: " + existingItem);
        log.info("Item atualizado para: " + updatedItem);

        return updatedItem;
    }
}
