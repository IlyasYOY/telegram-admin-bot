package ru.ilyasyoy.telegram.admin.domain.value.outcoming;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {
    private final List<SpecificMessageSender> senders;

    public void send(@NotNull OutcomingMessage outcomingMessage) {
        Objects.requireNonNull(outcomingMessage);

        for (SpecificMessageSender sender : senders) {
            if (sender.supports(outcomingMessage)) {
                sender.send(outcomingMessage);
            }
        }

        log.warn("Cannot find sender for {}", outcomingMessage);
    }
}
