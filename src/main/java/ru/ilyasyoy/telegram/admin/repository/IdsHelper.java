package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class IdsHelper {
    public void checkIdsNotExist(Collection<String> existingIds, Collection<String> newIds) {
        Collection<String> existingAnyKeys = findIdsThatAlreadyExist(existingIds, newIds);
        if (!existingAnyKeys.isEmpty()) {
            String message = "Cannot save: %s, they already exist".formatted(existingAnyKeys);
            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private Collection<String> findIdsThatAlreadyExist(
            Collection<String> existingIds, Collection<String> newIds) {
        return (Collection<String>) CollectionUtils.intersection(existingIds, newIds);
    }
}
