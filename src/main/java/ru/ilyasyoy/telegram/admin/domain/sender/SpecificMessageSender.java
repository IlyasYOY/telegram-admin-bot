package ru.ilyasyoy.telegram.admin.domain.sender;

import javax.validation.constraints.NotNull;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

public interface SpecificMessageSender<T extends OutcomingMessage> {
    @NotNull
    Class<T> getSupportedClass();

    default boolean supports(@NotNull T outcomingMessage) {
        var supportedClass = getSupportedClass();
        return outcomingMessage.getClass().equals(supportedClass);
    }

    void send(@NotNull T outcomingMessage);
}
