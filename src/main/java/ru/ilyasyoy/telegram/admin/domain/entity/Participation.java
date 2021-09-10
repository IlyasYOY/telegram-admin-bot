package ru.ilyasyoy.telegram.admin.domain.entity;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record Participation(@NotNull Chat chat, @NotNull User user, @NotNull State state) {

    public Participation(@NotNull Chat chat, @NotNull User user) {
        this(chat, user, State.INITIAL);
    }

    public boolean requiresConfirmation() {
        return state == State.NOT_CONFIRMED;
    }

    // TODO: Implement confirmation flow
    public static enum State {
        NOT_CONFIRMED,
        CONFIRMATION_SENT,
        CONFIRMED;

        public static State INITIAL = NOT_CONFIRMED;
    }
}
