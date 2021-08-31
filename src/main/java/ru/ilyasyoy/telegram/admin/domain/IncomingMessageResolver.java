package ru.ilyasyoy.telegram.admin.domain;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import ru.ilyasyoy.telegram.admin.domain.value.incoming.IncomingMessage;

public interface IncomingMessageResolver<T> {
    Optional<IncomingMessage> resolve(@NotNull T from);
}
