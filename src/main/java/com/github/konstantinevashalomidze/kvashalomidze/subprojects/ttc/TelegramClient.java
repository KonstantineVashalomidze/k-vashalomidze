package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class TelegramClient {

    private final RestClient restClient;

    public TelegramClient(
            RestClient.Builder builder,
            @Value("${telegram.bot.token}") String token
    ) {
        this.restClient = builder
                .baseUrl("https://api.telegram.org/bot" + token)
                .build();
    }

    /**
     * Send a plain-text or HTML message to a chat.
     */
    public void sendMessage(long chatId, String text) {
        restClient.post()
                .uri("/sendMessage")
                .body(Map.of(
                        "chat_id", chatId,
                        "text", text,
                        "parse_mode", "HTML"
                ))
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * Show a "typing..." indicator while the bot is processing.
     * Fire-and-forget — errors are silently ignored.
     */
    public void sendChatAction(long chatId) {
        try {
            restClient.post()
                    .uri("/sendChatAction")
                    .body(Map.of("chat_id", chatId, "action", "typing"))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ignored) {}
    }

    /**
     * Must be called after receiving a CallbackQuery to stop the loading
     * spinner on the inline button. Can optionally show a toast to the user.
     */
    public void answerCallbackQuery(String callbackQueryId) {
        try {
            restClient.post()
                    .uri("/answerCallbackQuery")
                    .body(Map.of("callback_query_id", callbackQueryId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ignored) {}
    }
}