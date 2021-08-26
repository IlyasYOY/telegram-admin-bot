package ru.ilyasyoy.telegram.admin.domain.value;

public record SimpleIncomingMessage(long chatId, String text) implements IncomingMessage {
    @Override
    public long getChatId() {
        return chatId;
    }
}
