package ru.ilyasyoy.telegram.admin.domain.processor;

import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessage;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

@Service
public class IncomingMessageProcessor {

    Optional<OutcomingMessage> process(@NotNull IncomingMessage incomingMessage) {
        Objects.requireNonNull(incomingMessage);

        return Optional.empty();
    }
}
