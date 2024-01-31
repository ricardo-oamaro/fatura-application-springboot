package br.com.backend.fatura.application.service;

import br.com.backend.fatura.application.model.item.Item;
import br.com.backend.fatura.application.repo.ItemRepository;
import br.com.backend.fatura.application.service.implementation.ItemServiceInterface;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements ItemServiceInterface {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item deleteItem(ObjectId id) throws ResourceNotFoundException {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        itemRepository.delete(item);
        return item;
    }

    @Override
    public Item updateItem(ObjectId id, Item item) throws ResourceNotFoundException {
        Item itemVar = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        itemVar.setDate(itemVar.getDate());
        itemVar.setDescription(itemVar.getDescription());
        itemVar.setDate(itemVar.getDate());

        itemRepository.save(item);

        return itemVar;
    }
}
