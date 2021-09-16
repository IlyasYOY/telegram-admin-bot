package ru.ilyasyoy.telegram.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import lombok.Getter;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ilyasyoy.telegram.admin.EasyRandomFeatures;

class MemoryUserDomainRepositoryTest implements EasyRandomFeatures {
    private final MemoryUserDomainRepository repo = new MemoryUserDomainRepository();
    @Getter private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void testSave() {
        var user = getRandomUser();

        repo.save(user);
        var foundUser = repo.findById(user.telegramId());

        assertThat(foundUser).isPresent().contains(user);
    }

    @Test
    void testFindNonExisting() {
        var telegramId = easyRandom.nextLong();

        var result = repo.findById(telegramId);

        assertThat(result).isEmpty();
    }

    @Test
    void testSaveAll() {
        var users = getRandomUsers(10).toList();

        repo.saveAll(users);

        assertThat(repo.findAll()).hasSize(10).hasSameElementsAs(users);
    }

    @Test
    void testDeletById() {
        var users = getRandomUsers(10).toList();
        var firstUsers = users.get(0);

        repo.saveAll(users);
        repo.deleteById(firstUsers.telegramId());
        var foundUsers = repo.findAll();

        assertThat(foundUsers).isNotEmpty().hasSize(9).doesNotContain(firstUsers);
    }

    @Test
    void testUpdateNotExistingRecord() {
        var user = getRandomUser();

        var updated = repo.update(user);
        var updatedUser = repo.findById(user.telegramId());

        assertAll(() -> assertThat(updated).isFalse(), () -> assertThat(updatedUser).isEmpty());
    }

    @Test
    void testUpdatedExistingRecord() {
        String myName = "Ilia";
        var user = getRandomUser();

        repo.save(user);
        var updated = repo.update(user.withUsername(myName));
        var updatedUser = repo.findById(user.telegramId());

        assertAll(
                () -> assertThat(updated).isTrue(),
                () ->
                        assertThat(updatedUser)
                                .isPresent()
                                .hasValueSatisfying(x -> x.username().equals(myName)));
    }

    @Test
    void testFindByUserNameAbsent() {
        var users = getRandomUsers(10).toList();

        repo.saveAll(users);

        var userOpitonal = repo.findByUsername("Ilia");

        assertThat(userOpitonal).isEmpty();
    }

    @Test
    void testFindByUserNamePresent() {
        var users = getRandomUsers(10).toList();
        var searchForUser = users.get(0);

        repo.saveAll(users);

        var userOpitonal = repo.findByUsername(searchForUser.username());

        assertThat(userOpitonal).contains(searchForUser).isPresent();
    }

    @Test
    void testDeleteByUserNamePresent() {
        var users = getRandomUsers(10).toList();
        var searchForUser = users.get(0);

        repo.saveAll(users);

        repo.deleteByUsername(searchForUser.username());

        assertThat(repo.findAll()).noneMatch(searchForUser::equals);
    }
}
