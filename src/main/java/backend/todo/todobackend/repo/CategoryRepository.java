package backend.todo.todobackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.todo.todobackend.entity.Category;

import java.util.List;


// You can immediately use all CRUD methods (Create, Read, Update, Delete)
// OOP principle: abstraction-implementation — here we describe all available ways to access data
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
