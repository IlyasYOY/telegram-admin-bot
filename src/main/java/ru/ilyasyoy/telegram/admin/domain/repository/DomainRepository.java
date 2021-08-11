package ru.ilyasyoy.telegram.admin.domain.repository;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

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
