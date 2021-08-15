package ru.ilyasyoy.telegram.admin.domain.entity;

import lombok.Builder;

@Builder
public record Chat(String telegramId, String name) {}
