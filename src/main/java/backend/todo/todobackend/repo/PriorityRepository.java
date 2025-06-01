package backend.todo.todobackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.todo.todobackend.entity.Priority;


import java.util.List;

// OOP principle: abstraction-implementation — here we describe all available ways to access data
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {



}

