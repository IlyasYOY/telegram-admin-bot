package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public record ChatStatusIncomingMessage(
        long chatId,
        long userId,
        @NotNull String username,
        @NotNull ChatStatusUpdateType status,
        @Nullable String chatName)
        implements IncomingMessage {

    @Override
    public long getChatId() {
        return chatId;
    }
}
