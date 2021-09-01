package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.repository.ChatDomainRepository;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.SimpleOutcomingMessage;

@Slf4j
@Service
@RequiredArgsConstructor
class MyChatStatusSpecificIncomingMessageProcessor
        implements SpecificIncomingMessageProcessor<MyChatStatusIncomingMessage> {
    private final ChatDomainRepository chatDomainRepository;

    @Override
    public @NotNull Class<MyChatStatusIncomingMessage> getSupportedClass() {
        return MyChatStatusIncomingMessage.class;
    }

    @Override
    public Optional<OutcomingMessage> process(
            @NotNull MyChatStatusIncomingMessage incomingMessage) {
        long chatId = incomingMessage.chatId();
        ChatStatusUpdateType status = incomingMessage.status();
        String chatName = incomingMessage.chatName();

        if (status == ChatStatusUpdateType.ADDED) {
            Chat chat = new Chat(chatId, chatName);

            chatDomainRepository.save(chat);

            OutcomingMessage outcomingMessage =
                    new SimpleOutcomingMessage(chatId, "Hello, it's me! Admin Bot!");

            log.info("Bot was added in chat: {}", chat);

            return Optional.of(outcomingMessage);
        } else if (status == ChatStatusUpdateType.REMOVED) {
            chatDomainRepository
                    .findByTelegramId(chatId)
                    .ifPresent(
                            foundChat -> {
                                log.info("Bot was removed from chat: {}", foundChat);
                                chatDomainRepository.update(foundChat.deactivate());
                            });
        }

        return null;
    }
}
