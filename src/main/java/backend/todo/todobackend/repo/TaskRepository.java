package backend.todo.todobackend.repo;

import backend.todo.todobackend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // for findAllByUserId
    List<Task> findAllByUser_Id(Long userId);

    // for find tasks by category
    List<Task> findByCategory_Id(Long categoryId);
}
