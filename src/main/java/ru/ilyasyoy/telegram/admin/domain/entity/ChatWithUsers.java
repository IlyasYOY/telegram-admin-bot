package ru.ilyasyoy.telegram.admin.domain.entity;

import java.util.List;
import lombok.Builder;
import lombok.Singular;

@Builder
public record ChatWithUsers(Chat chat, @Singular List<User> users) {}
