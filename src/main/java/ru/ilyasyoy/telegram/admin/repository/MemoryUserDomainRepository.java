package ru.ilyasyoy.telegram.admin.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.UserDomainRepository;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(UserDomainRepository.class)
public final class MemoryUserDomainRepository implements UserDomainRepository {
    private final Map<String, User> data = new ConcurrentHashMap<>();
    private final IdsHelper idsHelper;

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

        idsHelper.checkIdsNotExist(data.keySet(), usersByTelegramId.keySet());

        data.putAll(usersByTelegramId);
    }

    @Override
    public void save(@NotNull User item) {
        Set<String> itemIdContainer = Collections.singleton(item.telegramId());
        idsHelper.checkIdsNotExist(data.keySet(), itemIdContainer);
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
}
