package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Stat;
import backend.todo.todobackend.repo.StatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;




@Service


@Transactional
public class StatService {

        private final StatRepository repository;

        public StatService(StatRepository repository) {
            this.repository = repository;
        }

        public Stat findStat(String email) {
            return repository.findByUserEmail(email);
        }

}
