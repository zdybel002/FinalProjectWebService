package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.TaskRepository;
import backend.todo.todobackend.repo.UserRepository;
import backend.todo.todobackend.service.TelegramService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NotificationScheduler {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final TelegramService tg;

    // ← constructor injection ensures these are initialized
    public NotificationScheduler(TaskRepository taskRepo,
                                 UserRepository userRepo,
                                 TelegramService tg) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.tg       = tg;
    }

    @Scheduled(fixedRate = 60_000)  // every minute
    public void checkDeadlines() {
        LocalDateTime now    = LocalDateTime.now();
        LocalDateTime cutoff = now.plusHours(1);

        // find all tasks due within the next hour and not done
        List<Task> soon = taskRepo
                .findAllByCompletedFalseAndTaskDateBetween(now, cutoff);

        for (Task t : soon) {
            User u = userRepo.findById(t.getUser().getId()).orElse(null);
            if (u != null && u.getTelegramChat() != null) {
                String text = String.format(
                        "⏰ \"%s\" due at %s",
                        t.getTitle(),
                        t.getTaskDate()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                );
                tg.sendTo(u.getTelegramChat(), text);
            }
        }
    }
}
