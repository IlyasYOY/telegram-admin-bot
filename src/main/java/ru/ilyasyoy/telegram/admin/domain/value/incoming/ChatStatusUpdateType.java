package ru.ilyasyoy.telegram.admin.domain.value.incoming;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatStatusUpdateType {
    ADDED("member"),
    REMOVED("left");

    public static List<ChatStatusUpdateType> chatStatusUpdateTypes =
            List.of(ChatStatusUpdateType.values());

    public static Optional<ChatStatusUpdateType> of(@Nullable String telegramStatus) {
        return chatStatusUpdateTypes.stream()
                .filter(chatStatus -> chatStatus.telegramStatus.equalsIgnoreCase(telegramStatus))
                .findFirst();
    }

    private final String telegramStatus;
}
