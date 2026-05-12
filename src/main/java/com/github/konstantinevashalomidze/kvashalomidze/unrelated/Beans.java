package com.github.konstantinevashalomidze.kvashalomidze.unrelated;

import com.github.konstantinevashalomidze.kvashalomidze.unrelated.schedulers.TTCWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class Beans {

    @Bean
    public TTCWrapper ttcWrapper() {
        return new TTCWrapper();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(TTCWrapper ttcWrapper) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(ttcWrapper);
        return api;
    }
}
