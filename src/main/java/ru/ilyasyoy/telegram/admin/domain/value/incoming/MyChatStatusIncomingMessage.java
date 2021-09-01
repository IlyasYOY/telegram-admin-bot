package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public record MyChatStatusIncomingMessage(
        long chatId, @NotNull ChatStatusUpdateType status, @Nullable String chatName)
        implements IncomingMessage {

    @Override
    public long getChatId() {
        return chatId;
    }
}
