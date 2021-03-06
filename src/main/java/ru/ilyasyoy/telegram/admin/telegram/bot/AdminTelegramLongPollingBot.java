package ru.ilyasyoy.telegram.admin.telegram.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ilyasyoy.telegram.admin.configuration.annotation.Bot;
import ru.ilyasyoy.telegram.admin.configuration.property.TelegramBotProperties;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessageProcessor;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessageResolver;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.MessageSender;

@Bot
@Slf4j
@RequiredArgsConstructor
public final class AdminTelegramLongPollingBot extends TelegramLongPollingBot {
    @Delegate private final TelegramBotProperties telegramAdminBotProperties;
    private final IncomingMessageResolver<Update> incomingMessageResolver;
    private final IncomingMessageProcessor incomingMessageProcessor;
    private final MessageSender messageSender;

    @Override
    @SneakyThrows
    public void onRegister() {
        log.info("Bot was registered: {}", getMe());
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Update received: {}", update);
        incomingMessageResolver
                .resolve(update)
                .flatMap(
                        incomingMessage -> {
                            log.debug("Incoming message received: {}", incomingMessage);
                            return incomingMessageProcessor.process(incomingMessage);
                        })
                .ifPresentOrElse(
                        outcomingMessage -> {
                            log.debug("Outcoming message received: {}", outcomingMessage);
                            messageSender.send(outcomingMessage);
                        },
                        () -> log.warn("Processed with empty outcome: {}", update));
    }
}
