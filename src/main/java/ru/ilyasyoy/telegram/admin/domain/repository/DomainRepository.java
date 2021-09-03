package ru.ilyasyoy.telegram.admin.domain.repository;

import java.util.Collection;
import java.util.Optional;
import javax.validation.constraints.NotNull;

public interface DomainRepository<K, T> {

    Collection<T> findAll();

    Optional<T> findById(@NotNull K id);

    void saveAll(Collection<T> collection);

    void save(@NotNull T item);

    void deleteAll();

    void deleteById(@NotNull K id);

    boolean update(@NotNull T item);
}
