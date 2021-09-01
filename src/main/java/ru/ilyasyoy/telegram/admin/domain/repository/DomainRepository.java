package ru.ilyasyoy.telegram.admin.domain.repository;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface DomainRepository<T> {

    Collection<T> findAll();

    Optional<T> findByTelegramId(long telegramId);

    void saveAll(Collection<T> collection);

    void save(@NotNull T item);

    void deleteAll();

    void deleteByTelegramId(long telegramId);

    boolean update(@NotNull T item);
}
