package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.TaskRepository;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Scheduler component responsible for sending notifications to users via Telegram
 * if they have tasks due within the next hour.
 */
@Component
public class NotificationScheduler {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final TelegramService tg;

    /**
     * Constructor for injecting required dependencies.
     *
     * @param taskRepo repository for accessing tasks
     * @param userRepo repository for accessing users
     * @param tg       service for sending Telegram messages
     */
    public NotificationScheduler(TaskRepository taskRepo,
                                 UserRepository userRepo,
                                 TelegramService tg) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.tg       = tg;
    }

    /**
     * Runs every fixed interval (default 10 minutes).
     * Scans for tasks due within the next hour and sends Telegram reminders to users.
     */
    @Scheduled(fixedRateString = "${taskscheduler.check.rate:600000}")
    public void checkDeadlines() {
        LocalDateTime now    = LocalDateTime.now();
        LocalDateTime cutoff = now.plusHours(1);
        System.out.println("Scheduler firing: now=" + now + ", cutoff=" + cutoff);

        List<Task> soon = taskRepo
                .findAllByCompletedFalseAndTaskDateBetween(now, cutoff);
        System.out.println(" -> found " + soon.size() + " tasks");

        for (Task t : soon) {
            User u = userRepo.findById(t.getUser().getId()).orElse(null);
            if (u == null) {
                System.out.println("   no user for task " + t.getId());
                continue;
            }
            if (u.getTelegramChat() == null) {
                System.out.println("   user " + u.getId() + " has no chatId");
                continue;
            }
            String msg = String.format(
                    "⏰ \"%s\" due at %s",
                    t.getTitle(),
                    t.getTaskDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            );
            System.out.println("   sending to chat=" + u.getTelegramChat() + ": " + msg);

            try {
                tg.sendTo(u.getTelegramChat(), msg);
            } catch (Exception ex) {
                System.err.println("   Telegram send failed: " + ex);
            }
        }
    }
}
