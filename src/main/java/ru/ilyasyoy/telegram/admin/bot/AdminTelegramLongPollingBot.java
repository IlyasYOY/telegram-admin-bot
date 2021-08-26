package ru.ilyasyoy.telegram.admin.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ilyasyoy.telegram.admin.configuration.annotation.Bot;
import ru.ilyasyoy.telegram.admin.configuration.property.TelegramBotProperties;
import ru.ilyasyoy.telegram.admin.domain.value.ChatStatusIncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.ChatStatusUpdateType;

@Bot
@Slf4j
@RequiredArgsConstructor
public final class AdminTelegramLongPollingBot extends TelegramLongPollingBot {
    @Delegate private final TelegramBotProperties telegramAdminBotProperties;

    @Override
    @SneakyThrows
    public void onRegister() {
        log.info("Bot was registered: {}", getMe());
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Update received: {}", update);

        if (update.hasMyChatMember()) {
            var myChatStatus = update.getMyChatMember();
            var id = myChatStatus.getChat().getId();
            var status = myChatStatus.getNewChatMember().getStatus();

            var parsedChatStatus =
                    ChatStatusUpdateType.of(status)
                            .orElseThrow(() -> new IllegalArgumentException(status));

            var chatStatusIncomingMessage = new ChatStatusIncomingMessage(id, parsedChatStatus);
            log.debug(chatStatusIncomingMessage.toString());
        }
    }
}
