package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.repository.ChatDomainRepository;

@Component
@RequiredArgsConstructor
public class MemoryChatDomainRepository implements ChatDomainRepository {
    private final Map<Long, Chat> data = new ConcurrentHashMap<>();

    @Override
    public Collection<Chat> findAll() {
        return data.values();
    }

    @Override
    public Optional<Chat> findByTelegramId(long telegramId) {
        Chat chat = data.get(telegramId);
        if (chat == null) {
            return Optional.empty();
        }

        return Optional.of(chat);
    }

    @Override
    public void saveAll(Collection<Chat> collection) {
        Map<Long, Chat> telegramIdOnChats =
                collection.stream()
                        .collect(Collectors.toMap(Chat::telegramId, Function.identity()));

        data.putAll(telegramIdOnChats);
    }

    @Override
    public void save(@NotNull Chat item) {
        Objects.requireNonNull(item);

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
    public boolean update(@NotNull Chat item) {
        Objects.requireNonNull(item);

        Chat prevValue =
                data.computeIfPresent(
                        item.telegramId(), (key, value) -> value == null ? null : item);

        return prevValue != null;
    }
}
