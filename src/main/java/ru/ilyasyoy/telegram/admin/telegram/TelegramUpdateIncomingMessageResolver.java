package ru.ilyasyoy.telegram.admin.telegram;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.ChatStatusIncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.ChatStatusUpdateType;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessageResolver;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.MyChatStatusIncomingMessage;

@Service
public class TelegramUpdateIncomingMessageResolver implements IncomingMessageResolver<Update> {

    @Override
    public Optional<IncomingMessage> resolve(@NotNull Update update) {
        Objects.requireNonNull(update);

        if (update.hasMyChatMember()) {
            var myChatStatus = update.getMyChatMember();
            Chat chat = myChatStatus.getChat();
            long id = chat.getId();
            String chatName = chat.getTitle();
            String status = myChatStatus.getNewChatMember().getStatus();

            return ChatStatusUpdateType.of(status)
                    .map(
                            parsedStatus ->
                                    new MyChatStatusIncomingMessage(id, parsedStatus, chatName));
        }

        if (update.hasChatMember()) {
            ChatMemberUpdated chatMember = update.getChatMember();

            Chat chat = chatMember.getChat();
            User from = chatMember.getFrom();

            long userId = from.getId();
            String username = from.getUserName();
            long id = chat.getId();
            String chatName = chat.getTitle();
            String status = chatMember.getNewChatMember().getStatus();

            return ChatStatusUpdateType.of(status)
                    .map(
                            parsedStatus ->
                                    new ChatStatusIncomingMessage(
                                            id, userId, username, parsedStatus, chatName));
        }

        return Optional.empty();
    }
}
