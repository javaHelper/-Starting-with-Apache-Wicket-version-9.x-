package app.repos;

import app.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoMongoDBRepository extends CrudRepository<Todo, String> {
}
