package ru.ilyasyoy.telegram.admin.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.ChatDomainRepository;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(ChatDomainRepository.class)
public final class MemoryChatDomainRepository implements ChatDomainRepository {
  private final Map<String, User> data = new ConcurrentHashMap<>();
  private final IdsHelper idsHelper;

  @Override
  public Collection<Chat> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<Chat> findByTelegramId(@NotNull String telegramId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Optional<Chat> findByUsername(@NotNull String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void saveAll(Collection<Chat> collection) {
    // TODO Auto-generated method stub

  }

  @Override
  public void save(@NotNull Chat item) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteAll() {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteByTelegramId(@NotNull String telegramId) {
    // TODO Auto-generated method stub

  }

  @Override
  public void deleteByUsername(@NotNull String username) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean update(@NotNull Chat item) {
    // TODO Auto-generated method stub
    return false;
  }
}
