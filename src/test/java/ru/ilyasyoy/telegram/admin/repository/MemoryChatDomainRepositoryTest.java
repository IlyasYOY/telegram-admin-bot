package ru.ilyasyoy.telegram.admin.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;

class MemoryChatDomainRepositoryTest {

    private final IdsHelper idsHelper = new IdsHelper();
    private final MemoryChatDomainRepository repo = new MemoryChatDomainRepository(idsHelper);

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void testSave() {
        var telegramId = UUID.randomUUID().toString();
        var name = "Cozy Chat";

        var chat = Chat.builder().telegramId(telegramId).name(name).build();

        repo.save(chat);

        Collection<Chat> allItems = repo.findAll();
        Optional<Chat> foundChat = repo.findByTelegramId(telegramId);

        assertAll(
                () -> assertTrue(foundChat.isPresent()),
                () -> assertEquals(1, allItems.size()),
                () -> assertEquals(chat, foundChat.get()));
    }
}
