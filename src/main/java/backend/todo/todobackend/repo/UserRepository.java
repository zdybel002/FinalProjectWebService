package backend.todo.todobackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.todo.todobackend.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
