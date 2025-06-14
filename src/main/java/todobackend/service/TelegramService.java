package backend.todo.todobackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Service for sending messages to Telegram users via Bot API.
 */
@Service
public class TelegramService {

    /**
     * Telegram bot token injected from application properties.
     */
    @Value("${telegram.bot.token}")
    private String token;

    private final RestTemplate rest = new RestTemplate();

    /**
     * Sends a text message to a specific Telegram chat.
     *
     * @param chatId the chat ID of the recipient
     * @param text the message content
     */
    public void sendTo(Long chatId, String text) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://api.telegram.org/bot" + token + "/sendMessage")
                .queryParam("chat_id", chatId)
                .queryParam("text", text)
                .encode()
                .build()
                .toUri();

        rest.getForObject(uri, String.class);
    }
}
