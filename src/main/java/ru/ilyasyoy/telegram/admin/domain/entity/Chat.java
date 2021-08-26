package ru.ilyasyoy.telegram.admin.domain.entity;

import javax.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Chat(@NotNull Long telegramId, @NotNull String name) {}
