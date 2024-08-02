package com.pet.reminder_app.service;

import com.pet.reminder_app.config.TelegramBotConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {

    private final TelegramBotConfig telegramBotConfig;

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getBotToken();
    }

    public String getChannelChatId() {
        return telegramBotConfig.getChannelChatId();
    }

}
