package br.com.backend.fatura.application.repo;

import br.com.backend.fatura.application.model.item.Item;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, ObjectId> {
}
