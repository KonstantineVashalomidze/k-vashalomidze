package com.github.konstantinevashalomidze.kvashalomidze.unrelated.schedulers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TTCWrapper extends TelegramLongPollingBot {

    public TTCWrapper() {
        super("8698861100:AAEVfB1x-DRnv_xrT4VXoslEopI4u_K3-rc");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (text) {
                case "/გაჩერდი" -> sendMsg(chatId, "გავჩერდი");
                case "/გააგრძელე" -> sendMsg(chatId, "გავაგრძელე");
                default -> sendMsg(chatId, "ბრძანება ვერ გავიგე");
            }
        }
    }

    public void sendMsg(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return "8698861100:AAEVfB1x-DRnv_xrT4VXoslEopI4u_K3-rc";
    }

    @Override
    public String getBotUsername() {
        return "BusArrivals";
    }
}