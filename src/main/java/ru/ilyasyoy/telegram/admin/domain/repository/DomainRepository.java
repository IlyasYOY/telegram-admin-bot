package ru.ilyasyoy.telegram.admin.domain.repository;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface DomainRepository<T> {

  Collection<T> findAll();

  Optional<T> findByTelegramId(@NotNull String telegramId);

  Optional<T> findByUsername(@NotNull String username);

  void saveAll(Collection<T> collection);

  void save(@NotNull T item);

  void deleteAll();

  void deleteByTelegramId(@NotNull String telegramId);

  void deleteByUsername(@NotNull String username);

  boolean update(@NotNull T item);
}
