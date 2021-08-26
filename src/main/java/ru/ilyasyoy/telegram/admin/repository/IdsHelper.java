package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class IdsHelper {
    public <T> void checkIdsNotExist(Collection<T> existingIds, Collection<T> newIds) {
        Collection<T> existingAnyKeys =
                (Collection<T>) CollectionUtils.intersection(existingIds, newIds);
        if (!existingAnyKeys.isEmpty()) {
            String message = "Cannot save: %s, they already exist".formatted(existingAnyKeys);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
