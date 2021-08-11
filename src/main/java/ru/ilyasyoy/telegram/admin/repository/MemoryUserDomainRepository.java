package ru.ilyasyoy.telegram.admin.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.UserDomainRepository;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnMissingBean(UserDomainRepository.class)
public final class MemoryUserDomainRepository implements UserDomainRepository {
    private final Map<String, User> data = new ConcurrentHashMap<>();

    @Override
    public Collection<User> findAll() {
        return data.values();
    }

    @Override
    public Optional<User> findByTelegramId(@NotNull String telegramId) {
        User user = data.get(telegramId);
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(@NotNull String username) {
        return data.values()
                .stream()
                .filter(user -> user.isSameUsername(username))
                .findAny();
    }

    @Override
    public void saveAll(Collection<User> collection) {
        Map<String, User> usersByTelegramId = collection.stream()
                .collect(Collectors.toMap(User::telegramId, Function.identity()));

        checkIdsNotExist(usersByTelegramId.keySet());

        data.putAll(usersByTelegramId);
    }

    @Override
    public void save(@NotNull User item) {
        checkIdsNotExist(item.telegramId());
        data.put(item.telegramId(), item);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteByTelegramId(@NotNull String telegramId) {
        data.remove(telegramId);
    }

    @Override
    public void deleteByUsername(@NotNull String username) {
        data.values()
                .stream()
                .filter(user -> user.isSameUsername(username))
                .findFirst()
                .ifPresent(user -> data.remove(user.telegramId()));
    }

    @Override
    public boolean update(User item) {
        User user = data.get(item.telegramId());
        if (user.telegramId() == null) {
            return false;
        }
        data.remove(item.telegramId());
        return true;
    }

    private void checkIdsNotExist(String... ids) {
        Collection<String> existingAnyKeys = findIdsThatAlreadyExist(Arrays.stream(ids).toList());
        if (!existingAnyKeys.isEmpty()) {
            String message = "Cannot save: %s, they already exist".formatted(existingAnyKeys);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void checkIdsNotExist(Collection<String> ids) {
        Collection<String> existingAnyKeys = findIdsThatAlreadyExist(ids);
        if (!existingAnyKeys.isEmpty()) {
            String message = "Cannot save: %s, they already exist".formatted(existingAnyKeys);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private Collection<String> findIdsThatAlreadyExist(Collection<String> ids) {
        return CollectionUtils.intersection(ids, data.keySet());
    }
}
