package ru.ilyasyoy.telegram.admin.domain.value.incoming;

public record ChatStatusIncomingMessage(long chatId, ChatStatusUpdateType status)
        implements IncomingMessage {

    @Override
    public long getChatId() {
        return chatId;
    }
}
