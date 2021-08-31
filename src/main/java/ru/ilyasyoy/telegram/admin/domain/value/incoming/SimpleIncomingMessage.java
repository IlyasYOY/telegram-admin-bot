package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import javax.validation.constraints.NotNull;

public record SimpleIncomingMessage(long chatId, @NotNull String text) implements IncomingMessage {
    @Override
    public long getChatId() {
        return chatId;
    }
}
