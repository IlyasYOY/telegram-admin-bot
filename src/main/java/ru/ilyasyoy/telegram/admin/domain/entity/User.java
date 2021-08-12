package ru.ilyasyoy.telegram.admin.domain.entity;

import javax.validation.constraints.NotNull;

public record User(@NotNull String telegramId, @NotNull String username) {

  public boolean isSameUsername(@NotNull String username) {
    return this.username.equalsIgnoreCase(username);
  }
}
