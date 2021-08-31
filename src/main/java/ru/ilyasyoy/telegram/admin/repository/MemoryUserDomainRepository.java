package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.UserDomainRepository;

@Component
@RequiredArgsConstructor
public class MemoryUserDomainRepository implements UserDomainRepository {
    private final Map<Long, User> data = new ConcurrentHashMap<>();
    private final IdsHelper idsHelper;

    @Override
    public Collection<User> findAll() {
        return data.values();
    }

    @Override
    public Optional<User> findByTelegramId(long telegramId) {
        User user = data.get(telegramId);
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(@NotNull String username) {
        Objects.requireNonNull(username);

        return data.values().stream().filter(user -> user.isSameUsername(username)).findAny();
    }

    @Override
    public void saveAll(Collection<User> collection) {
        Map<Long, User> usersByTelegramId =
                collection.stream()
                        .collect(Collectors.toMap(User::telegramId, Function.identity()));

        idsHelper.checkIdsNotExist(data.keySet(), usersByTelegramId.keySet());

        data.putAll(usersByTelegramId);
    }

    @Override
    public void save(@NotNull User item) {
        Objects.requireNonNull(item);

        Set<Long> itemIdContainer = Collections.singleton(item.telegramId());
        idsHelper.checkIdsNotExist(data.keySet(), itemIdContainer);
        data.put(item.telegramId(), item);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteByTelegramId(long telegramId) {
        data.remove(telegramId);
    }

    @Override
    public void deleteByUsername(@NotNull String username) {
        Objects.requireNonNull(username);

        data.values().stream()
                .filter(user -> user.isSameUsername(username))
                .findFirst()
                .ifPresent(user -> data.remove(user.telegramId()));
    }

    @Override
    public boolean update(User item) {
        Objects.requireNonNull(item);

        User prevValue = data.putIfAbsent(item.telegramId(), item);
        if (prevValue == null) {
            return false;
        }
        return true;
    }
}
