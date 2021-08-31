package ru.ilyasyoy.telegram.admin.domain.value.outcoming;

import javax.validation.constraints.NotNull;

public record SimpleOutcomingMessage(long chatId, @NotNull String text)
        implements OutcomingMessage {

    @Override
    public long getChatId() {
        return chatId;
    }
}
