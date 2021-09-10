package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilyasyoy.telegram.admin.domain.value.outcoming.OutcomingMessage;

@Service
@RequiredArgsConstructor
public class IncomingMessageProcessor {
    private final List<SpecificIncomingMessageProcessor> processors;

    public Optional<OutcomingMessage> process(@NotNull IncomingMessage incomingMessage) {
        Objects.requireNonNull(incomingMessage);

        for (SpecificIncomingMessageProcessor<IncomingMessage> processor : processors) {
            if (processor.supports(incomingMessage)) {
                return processor.process(incomingMessage);
            }
        }

        return Optional.empty();
    }
}
