package backend.todo.todobackend.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.todo.todobackend.entity.Task;

import java.util.List;


// OOP principle: abstraction-implementation — here we describe all available ways to access data
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // find all tasks of a specific user
    List<Task> findByUserEmailOrderByTaskDateDesc(String email);

    //find
    List<Task> findByCategory_IdOrderByTaskDateAsc(Long categoryId);


}

