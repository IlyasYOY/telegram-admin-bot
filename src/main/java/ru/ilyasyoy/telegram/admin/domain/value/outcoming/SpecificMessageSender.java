package ru.ilyasyoy.telegram.admin.domain.value.outcoming;

import javax.validation.constraints.NotNull;

public interface SpecificMessageSender<T extends OutcomingMessage> {
    @NotNull
    Class<T> getSupportedClass();

    default boolean supports(@NotNull T outcomingMessage) {
        var supportedClass = getSupportedClass();
        return outcomingMessage.getClass().equals(supportedClass);
    }

    void send(@NotNull T outcomingMessage);
}
