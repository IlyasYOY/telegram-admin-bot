package ru.ilyasyoy.telegram.admin.telegram.sender;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.SimpleOutcomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.SpecificMessageSender;

@Service
public class SimpleOutcomingMessageSpecificessageSender
        implements SpecificMessageSender<SimpleOutcomingMessage> {
    private final AbsSender absSender;

    public SimpleOutcomingMessageSpecificessageSender(
        @Lazy AbsSender absSender
    ) {
        this.absSender = absSender;
    }

    @Override
    public @NotNull Class<SimpleOutcomingMessage> getSupportedClass() {
        return SimpleOutcomingMessage.class;
    }

    @Override
    @SneakyThrows
    public void send(@NotNull SimpleOutcomingMessage outcomingMessage) {
        absSender.execute(
                SendMessage.builder()
                        .chatId(String.valueOf(outcomingMessage.chatId()))
                        .text(outcomingMessage.text())
                        .build());
    }
}
