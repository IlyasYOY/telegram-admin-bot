package ru.ilyasyoy.telegram.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;

class MemoryChatDomainRepositoryTest {
    private final MemoryChatDomainRepository repo = new MemoryChatDomainRepository();
    private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void testSave() {
        var chat = getRandomChat();

        repo.save(chat);
        var foundChat = repo.findById(chat.telegramId());

        assertThat(foundChat).isPresent().contains(chat);
    }

    @Test
    void testFindNonExisting() {
        var telegramId = easyRandom.nextLong();

        var result = repo.findById(telegramId);

        assertThat(result).isEmpty();
    }

    @Test
    void testSaveAll() {
        var chats = getRandomChats(10).toList();

        repo.saveAll(chats);

        assertThat(repo.findAll()).hasSize(10).hasSameElementsAs(chats);
    }

    @Test
    void testDeletById() {
        var chats = getRandomChats(10).toList();
        var firstChat = chats.get(0);

        repo.saveAll(chats);
        repo.deleteById(firstChat.telegramId());
        var foundChats = repo.findAll();

        assertThat(foundChats).isNotEmpty().hasSize(9).doesNotContain(firstChat);
    }

    @Test
    void testUpdateNotExistingRecord() {
        var chat = getRandomChat();

        var updated = repo.update(chat);
        var updatedChat = repo.findById(chat.telegramId());

        assertAll(() -> assertThat(updated).isFalse(), () -> assertThat(updatedChat).isEmpty());
    }

    @Test
    void testUpdatedExistingRecord() {
        String myName = "Ilia";
        var chat = getRandomChat();

        repo.save(chat);
        var updated = repo.update(chat.withName(myName));
        var updatedChat = repo.findById(chat.telegramId());

        assertAll(
                () -> assertThat(updated).isTrue(),
                () ->
                        assertThat(updatedChat)
                                .isPresent()
                                .hasValueSatisfying(x -> x.name().equals(myName)));
    }

    Chat getRandomChat() {
        long telegramId = easyRandom.nextLong();
        String name = easyRandom.nextObject(String.class);
        return new Chat(telegramId, name);
    }

    Stream<Chat> getRandomChats(int size) {
        return IntStream.range(0, size).mapToObj(x -> getRandomChat());
    }
}
