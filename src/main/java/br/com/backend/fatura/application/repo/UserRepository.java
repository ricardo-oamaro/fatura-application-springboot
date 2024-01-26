package br.com.backend.fatura.application.repo;

import br.com.backend.fatura.application.model.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByEmail(String email);
}
