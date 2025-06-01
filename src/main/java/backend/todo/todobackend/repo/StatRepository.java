package backend.todo.todobackend.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.todo.todobackend.entity.Stat;


// OOP principle: abstraction-implementation — here we describe all available ways to access data
@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {


}
