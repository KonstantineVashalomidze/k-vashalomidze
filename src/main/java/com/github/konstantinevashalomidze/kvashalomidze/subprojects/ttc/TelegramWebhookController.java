package com.github.konstantinevashalomidze.kvashalomidze.subprojects.ttc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webhook")
public class TelegramWebhookController {

    private static final Logger log = LoggerFactory.getLogger(TelegramWebhookController.class);

    private final TelegramClient telegramClient;
    private final NearbyStopsService nearbyStopsService;
    private final NearbyStopsFormatter formatter;

    public TelegramWebhookController(
            TelegramClient telegramClient,
            NearbyStopsService nearbyStopsService,
            NearbyStopsFormatter formatter
    ) {
        this.telegramClient = telegramClient;
        this.nearbyStopsService = nearbyStopsService;
        this.formatter = formatter;
    }

    @PostMapping
    public ResponseEntity<Void> handleUpdate(@RequestBody TelegramUpdate update) {
        try {
            dispatch(update);
        } catch (Exception e) {
            log.error("Error handling update {}", update.updateId(), e);
        }
        // Always return 200 — Telegram will retry if we return anything else
        return ResponseEntity.ok().build();
    }

    private void dispatch(TelegramUpdate update) {
        // --- Inline keyboard button press ---
        if (update.callbackQuery() != null) {
            handleCallbackQuery(update.callbackQuery());
            return;
        }

        // anyMessage() covers both `message` (static location / text)
        // and `edited_message` (live location updates from Telegram)
        TelegramUpdate.Message message = update.anyMessage();
        if (message == null) return;

        long chatId = message.chat().id();

        // --- Static or live location ---
        if (message.location() != null) {
            handleLocation(chatId, message.location());
            return;
        }

        // --- Venue pin — treat its coordinates like a normal location ---
        if (message.venue() != null && message.venue().location() != null) {
            handleLocation(chatId, message.venue().location());
            return;
        }

        // --- Non-text messages (stickers, contacts, etc.) ---
        if (message.text() == null) {
            telegramClient.sendMessage(chatId, "Send me your 📍 location to find nearby stops.");
            return;
        }

        // --- Text commands ---
        // Strip @BotUsername suffix Telegram appends in group chats (e.g. /start@MyBot)
        String text = message.text().trim().replaceAll("@\\S+$", "");

        switch (text) {
            case "/start" -> telegramClient.sendMessage(chatId,
                    """
                    👋 <b>Welcome to Tbilisi Transit Bot!</b>

                    📍 Share your <b>location</b> and I'll show you the nearest bus stops with live arrival times.

                    Just tap the 📎 attachment button → <b>Location</b>.
                    """);

            case "/help" -> telegramClient.sendMessage(chatId,
                    """
                    <b>How to use:</b>
                    1. Tap the 📎 button
                    2. Choose <b>Location</b>
                    3. Send your current location

                    I'll find the 3 closest stops within 600 m and show upcoming arrivals.
                    🟢 = real-time   🕐 = scheduled
                    """);

            default -> telegramClient.sendMessage(chatId,
                    "Send me your 📍 location to find nearby stops, or type /help.");
        }
    }

    private void handleCallbackQuery(TelegramUpdate.CallbackQuery cq) {
        // Acknowledge immediately — stops the loading spinner on the button
        telegramClient.answerCallbackQuery(cq.id());

        if (cq.message() == null) return;
        long chatId = cq.message().chat().id();

        // Extend here for future inline keyboard actions (e.g. "🔄 Refresh")
        telegramClient.sendMessage(chatId, "Unknown action.");
    }

    private void handleLocation(long chatId, TelegramUpdate.Location location) {
        telegramClient.sendChatAction(chatId);

        List<NearbyStop> nearby = nearbyStopsService.findNearbyWithArrivals(
                location.latitude(),
                location.longitude()
        );

        telegramClient.sendMessage(chatId, formatter.format(nearby));
    }
}