package br.com.backend.fatura.application.controller;

import br.com.backend.fatura.application.model.item.Item;
import br.com.backend.fatura.application.service.ItemService;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public List<Item> getProducts() {
        return itemService.getItems();
    }

    @GetMapping("/total-price-and-items")
    @ResponseBody
    public Map<String, Object> getTotalPriceAndItems() {
        return itemService.getTotalPriceAndItems();
    }

    @PostMapping("/insert")
    public Item insert(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @PutMapping("/update/{id}")
    public Item update(@PathVariable ObjectId id, @RequestBody Item item ) throws ResourceNotFoundException {
        return itemService.updateItem( id, item);
    }

    @DeleteMapping("/delete/{id}")
    public Item delete(@PathVariable ObjectId id ) throws ResourceNotFoundException {
        return  itemService.deleteItem(id);
    }

}
