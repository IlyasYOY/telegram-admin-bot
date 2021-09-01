package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

interface SpecificIncomingMessageProcessor<T extends IncomingMessage> {
    @NotNull
    Class<T> getSupportedClass();

    default boolean supports(@NotNull T incomingMessage) {
        var supportedClass = getSupportedClass();
        return incomingMessage.getClass().equals(supportedClass);
    }

    Optional<OutcomingMessage> process(@NotNull T incomingMessage);
}
