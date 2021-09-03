package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.Participation;
import ru.ilyasyoy.telegram.admin.domain.repository.ParticipationDomainRepository;

@Component
public class MemoryParticipationDomainRepository implements ParticipationDomainRepository {
    private final Map<CompoundKey, Participation> data =
            new ConcurrentHashMap<CompoundKey, Participation>();

    @Override
    public Collection<Participation> findAll() {
        return data.values();
    }

    @Override
    public Optional<Participation> findById(@NotNull CompoundKey id) {
        Objects.requireNonNull(id);
        Participation participation = data.get(id);
        if (participation == null) {
            return Optional.empty();
        }
        return Optional.of(participation);
    }

    @Override
    public void saveAll(Collection<Participation> collection) {
        Map<CompoundKey, Participation> collect =
                collection.stream().collect(Collectors.toMap(CompoundKey::of, Function.identity()));

        data.putAll(collect);
    }

    @Override
    public void save(@NotNull Participation item) {
        Objects.requireNonNull(item);

        CompoundKey key = CompoundKey.of(item);

        data.put(key, item);
    }

    @Override
    public void deleteAll() {
        data.clear();
    }

    @Override
    public void deleteById(@NotNull CompoundKey id) {
        Objects.requireNonNull(id);

        data.remove(id);
    }

    @Override
    public boolean update(@NotNull Participation item) {
        Objects.requireNonNull(item);

        CompoundKey key = CompoundKey.of(item);

        Participation prevValue =
                data.computeIfPresent(key, (unusedKey, value) -> value == null ? null : item);

        return prevValue != null;
    }
}
