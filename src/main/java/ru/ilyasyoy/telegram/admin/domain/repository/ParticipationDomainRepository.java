package ru.ilyasyoy.telegram.admin.domain.repository;

import ru.ilyasyoy.telegram.admin.domain.entity.Participation;

public interface ParticipationDomainRepository
        extends DomainRepository<ParticipationDomainRepository.CompoundKey, Participation> {

    public static record CompoundKey(long chatId, long userId) {
        public static CompoundKey of(Participation participation) {
            Long userId = participation.user().telegramId();
            long chatId = participation.chat().telegramId();

            return new CompoundKey(chatId, userId);
        }
    }
}
