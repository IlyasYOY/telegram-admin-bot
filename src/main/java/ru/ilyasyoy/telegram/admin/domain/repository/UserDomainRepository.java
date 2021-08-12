package ru.ilyasyoy.telegram.admin.domain.repository;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import ru.ilyasyoy.telegram.admin.domain.entity.User;

public interface UserDomainRepository extends DomainRepository<User> {

    Optional<User> findByUsername(@NotNull String username);

    void deleteByUsername(@NotNull String username);
}
