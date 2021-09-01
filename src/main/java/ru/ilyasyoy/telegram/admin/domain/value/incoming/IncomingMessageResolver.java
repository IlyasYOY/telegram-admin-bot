package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IncomingMessageResolver<T> {
    Optional<IncomingMessage> resolve(@NotNull T from);
}
