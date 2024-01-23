package br.com.fatura.application.service;

import br.com.fatura.application.model.Product;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts();
    public Product addProduct(Product product);

    public Product deleteProduct(ObjectId id ) throws ResourceNotFoundException;

    public Product updateProduct(ObjectId id , Product product) throws ResourceNotFoundException;
}
