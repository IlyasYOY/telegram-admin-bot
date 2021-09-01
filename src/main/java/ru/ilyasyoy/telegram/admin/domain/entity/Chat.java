package ru.ilyasyoy.telegram.admin.domain.entity;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record Chat(long telegramId, @NotNull String name, boolean active) {
    public Chat(long telegramId, @NotNull String name) {
        this(telegramId, name, true);
    }

    public Chat deactivate() {
        return active ? this.withActive(false) : this;
    }

    public Chat activate() {
        return active ? this : this.withActive(true);
    }
}
