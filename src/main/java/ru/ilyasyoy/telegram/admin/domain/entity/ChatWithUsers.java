package ru.ilyasyoy.telegram.admin.domain.entity;

import java.util.List;

public record ChatWithUsers(Chat chat, List<User> users) {}
