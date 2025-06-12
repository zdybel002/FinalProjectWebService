package backend.todo.todobackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {
    @Value("${telegram.bot.token}")     private String token;
    private final RestTemplate rest = new RestTemplate();


    public void sendTo(Long chatId, String text) {
        String url = String.format(
                "https://api.telegram.org/bot%s/sendMessage?chat_id=%d&text=%s",
                token, chatId, UriUtils.encode(text, StandardCharsets.UTF_8)
        );
        rest.getForObject(url, String.class);
    }
}
