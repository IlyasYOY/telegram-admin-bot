package ru.ilyasyoy.telegram.admin.domain.entity;

import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Participation(@NotNull Chat chat, @NotNull User user, @NotNull State state) {

    public static enum State {
        NOT_CONFIRMED,
        CONFIRMATION_SENT,
        CONFIRMED;
    }
}
