package backend.todo.todobackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {
    @Value("${telegram.bot.token}")
    private String token;

    private final RestTemplate rest = new RestTemplate();

    public void sendTo(Long chatId, String text) {
        // Build the URI with query params, then let Spring encode only the params
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://api.telegram.org/bot" + token + "/sendMessage")
                .queryParam("chat_id", chatId)
                .queryParam("text", text)
                .encode()      // <— only percent-encodes the values, not the entire path
                .build()
                .toUri();

        rest.getForObject(uri, String.class);
    }
}