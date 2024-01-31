package br.com.backend.fatura.application.controller;

import br.com.backend.fatura.application.model.item.Item;
import br.com.backend.fatura.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public List<Item> getProducts() {
        return itemService.getItems();
    }

}
