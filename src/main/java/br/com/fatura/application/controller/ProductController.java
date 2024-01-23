package br.com.fatura.application.controller;

import br.com.fatura.application.model.Product;
import br.com.fatura.application.service.ProductService;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/insert")
    public Product insert(@RequestBody Product product) {
        return productService.addProduct(product);
    }
    @PutMapping("/update/{id}")
    public Product update(@PathVariable ObjectId id,@RequestBody Product product ) throws ResourceNotFoundException {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public Product delete(@PathVariable ObjectId id ) throws ResourceNotFoundException {
        return  productService.deleteProduct(id);
    }
}
