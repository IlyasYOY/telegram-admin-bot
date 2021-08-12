package ru.ilyasyoy.telegram.admin.bot;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ilyasyoy.telegram.admin.configuration.annotation.Bot;
import ru.ilyasyoy.telegram.admin.configuration.property.TelegramBotProperties;

@Bot
@Slf4j
@RequiredArgsConstructor
public final class AdminTelegramLongPollingBot extends TelegramLongPollingBot {
    @Delegate private final TelegramBotProperties telegramAdminBotProperties;

    @Override
    public void onRegister() {
        log.info("Bot was registered: {}", this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Update received: {}", update);
    }
}
