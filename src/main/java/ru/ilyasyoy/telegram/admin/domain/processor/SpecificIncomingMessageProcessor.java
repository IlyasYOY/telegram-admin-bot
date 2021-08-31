package ru.ilyasyoy.telegram.admin.domain.processor;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

interface SpecificIncomingMessageProcessor<T extends IncomingMessage> {
    @NotNull
    Class<T> getSupportedClass();

    default boolean supports(T incomingMessage) {
        var supportedClass = getSupportedClass();
        return incomingMessage.getClass().equals(supportedClass);
    }

    Optional<OutcomingMessage> process(@NotNull T incomingMessage);
}
