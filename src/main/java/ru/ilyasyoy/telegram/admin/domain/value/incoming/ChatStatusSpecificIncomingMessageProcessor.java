package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.entity.Participation;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.ChatDomainRepository;
import ru.ilyasyoy.telegram.admin.domain.repository.ParticipationDomainRepository;
import ru.ilyasyoy.telegram.admin.domain.repository.ParticipationDomainRepository.CompoundKey;
import ru.ilyasyoy.telegram.admin.domain.repository.UserDomainRepository;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatStatusSpecificIncomingMessageProcessor
        implements SpecificIncomingMessageProcessor<ChatStatusIncomingMessage> {
    private final UserDomainRepository userRepository;
    private final ChatDomainRepository chatRepository;
    private final ParticipationDomainRepository participationRepository;

    @Override
    public @NotNull Class<ChatStatusIncomingMessage> getSupportedClass() {
        return ChatStatusIncomingMessage.class;
    }

    @Override
    public Optional<OutcomingMessage> process(@NotNull ChatStatusIncomingMessage incomingMessage) {
        Objects.requireNonNull(incomingMessage);

        long chatId = incomingMessage.chatId();
        Chat chat =
                chatRepository
                        .findById(chatId)
                        .orElseThrow(
                                () -> {
                                    log.error("No chat available for {}", incomingMessage);
                                    return new IllegalStateException("Cannot find chat");
                                });

        long userId = incomingMessage.userId();
        String username = incomingMessage.username();
        User user =
                userRepository
                        .findById(userId)
                        .orElseGet(
                                () -> {
                                    User userToSave = new User(userId, username);
                                    userRepository.save(userToSave);
                                    return userToSave;
                                });

        participationRepository
                .findById(new CompoundKey(chatId, userId))
                .orElseGet(
                        () -> {
                            Participation participationToSave = new Participation(chat, user);
                            participationRepository.save(participationToSave);
                            return participationToSave;
                        });

        return Optional.empty();
    }
}
