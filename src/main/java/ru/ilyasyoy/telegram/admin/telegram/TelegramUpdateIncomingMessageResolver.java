package ru.ilyasyoy.telegram.admin.telegram;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ilyasyoy.telegram.admin.domain.IncomingMessageResolver;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.ChatStatusUpdateType;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.MyChatStatusIncomingMessage;

@Service
public class TelegramUpdateIncomingMessageResolver implements IncomingMessageResolver<Update> {

    @Override
    public Optional<IncomingMessage> resolve(@NotNull Update update) {
        Objects.requireNonNull(update);

        if (update.hasMyChatMember()) {
            var myChatStatus = update.getMyChatMember();
            var id = myChatStatus.getChat().getId();
            var status = myChatStatus.getNewChatMember().getStatus();

            return ChatStatusUpdateType.of(status)
                    .map(parsedStatus -> new MyChatStatusIncomingMessage(id, parsedStatus));
        }

        return Optional.empty();
    }
}
