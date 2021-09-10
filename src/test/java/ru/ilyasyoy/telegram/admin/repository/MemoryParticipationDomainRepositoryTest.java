package ru.ilyasyoy.telegram.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.entity.Participation;
import ru.ilyasyoy.telegram.admin.domain.entity.Participation.State;
import ru.ilyasyoy.telegram.admin.domain.entity.User;
import ru.ilyasyoy.telegram.admin.domain.repository.ParticipationDomainRepository.CompoundKey;

class MemoryParticipationDomainRepositoryTest {
    private final MemoryParticipationDomainRepository repo =
            new MemoryParticipationDomainRepository();
    private EasyRandom easyRandom = new EasyRandom();

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void testSave() {
        var participation = getRandomParticipation();

        repo.save(participation);
        var foundChat = repo.findById(CompoundKey.of(participation));

        assertThat(foundChat).isPresent().contains(participation);
    }

    @Test
    void testFindNonExisting() {
        var participation = getRandomParticipation();

        var result = repo.findById(CompoundKey.of(participation));

        assertThat(result).isEmpty();
    }

    @Test
    void testSaveAll() {
        var participations = getRandomParticipations(10).toList();

        repo.saveAll(participations);

        assertThat(repo.findAll()).hasSize(10).hasSameElementsAs(participations);
    }

    @Test
    void testDeletById() {
        var participations = getRandomParticipations(10).toList();
        var firstParticipation = participations.get(0);

        repo.saveAll(participations);
        repo.deleteById(CompoundKey.of(firstParticipation));
        var foundParticipation = repo.findAll();

        assertThat(foundParticipation).isNotEmpty().hasSize(9).doesNotContain(firstParticipation);
    }

    @Test
    void testUpdateNotExistingRecord() {
        var participation = getRandomParticipation();

        var updated = repo.update(participation);
        var updatedParticipation = repo.findById(CompoundKey.of(participation));

        assertAll(
                () -> assertThat(updated).isFalse(),
                () -> assertThat(updatedParticipation).isEmpty());
    }

    @Test
    void testUpdatedExistingRecord() {
        var participation = getRandomParticipation();

        repo.save(participation);
        var updated = repo.update(participation.withState(State.CONFIRMED));
        var updatedParticipation = repo.findById(CompoundKey.of(participation));

        assertAll(
                () -> assertThat(updated).isTrue(),
                () ->
                        assertThat(updatedParticipation)
                                .isPresent()
                                .hasValueSatisfying(x -> x.state().equals(State.CONFIRMED)));
    }

    Participation getRandomParticipation() {
        var chat = new Chat(easyRandom.nextLong(), easyRandom.nextObject(String.class));
        var user = new User(easyRandom.nextLong(), easyRandom.nextObject(String.class));

        return new Participation(chat, user);
    }

    Stream<Participation> getRandomParticipations(int size) {
        return IntStream.range(0, size).mapToObj(x -> getRandomParticipation());
    }
}
