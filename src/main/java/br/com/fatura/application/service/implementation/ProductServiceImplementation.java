package br.com.fatura.application.service.implementation;

import br.com.fatura.application.model.Product;
import br.com.fatura.application.repo.ProductRepository;
import br.com.fatura.application.service.ProductService;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImplementation implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product deleteProduct(ObjectId id) throws ResourceNotFoundException {
        Product product = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
        return product;
    }

    @Override
    public Product updateProduct(ObjectId id, Product product) throws ResourceNotFoundException {
        Product productVar = productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productVar.setDate(productVar.getDate());
        productVar.setDescription(productVar.getDescription());
        productVar.setValue(productVar.getValue());
        productRepository.save(product);
        return productVar;
    }
}
