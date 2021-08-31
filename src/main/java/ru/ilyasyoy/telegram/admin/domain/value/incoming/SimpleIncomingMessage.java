package ru.ilyasyoy.telegram.admin.domain.value.incoming;

public record SimpleIncomingMessage(long chatId, String text) implements IncomingMessage {
    @Override
    public long getChatId() {
        return chatId;
    }
}
