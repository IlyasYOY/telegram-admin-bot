package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import javax.validation.constraints.NotNull;

public record MyChatStatusIncomingMessage(long chatId, @NotNull ChatStatusUpdateType status)
        implements IncomingMessage {

    @Override
    public long getChatId() {
        return chatId;
    }
}
