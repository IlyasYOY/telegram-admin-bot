package ru.ilyasyoy.telegram.admin.domain.processor;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.ChatStatusUpdateType;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.MyChatStatusIncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.SimpleOutcomingMessage;

@Service
class MyChatStatusSpecificIncomingMessageProcessor
        implements SpecificIncomingMessageProcessor<MyChatStatusIncomingMessage> {

    @Override
    public @NotNull Class<MyChatStatusIncomingMessage> getSupportedClass() {
        return MyChatStatusIncomingMessage.class;
    }

    @Override
    public Optional<OutcomingMessage> process(
            @NotNull MyChatStatusIncomingMessage incomingMessage) {
        long chatId = incomingMessage.chatId();
        ChatStatusUpdateType status = incomingMessage.status();

        if (status == ChatStatusUpdateType.ADDED) {
            OutcomingMessage outcomingMessage =
                    new SimpleOutcomingMessage(chatId, "Hello, it's me! Admin Bot!");
            return Optional.of(outcomingMessage);
        }

        return null;
    }
}
