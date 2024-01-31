package br.com.backend.fatura.application.service.implementation;

import br.com.backend.fatura.application.model.item.Item;
import edu.umd.cs.findbugs.classfile.ResourceNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

public interface ItemServiceInterface {

    public List<Item> getItems();
    public Item addItem(Item item);

    public Item deleteItem(ObjectId id ) throws ResourceNotFoundException;

    public Item updateItem(ObjectId id, Item item) throws ResourceNotFoundException;
}
