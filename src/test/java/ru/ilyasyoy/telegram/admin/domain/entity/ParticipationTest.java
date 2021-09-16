package ru.ilyasyoy.telegram.admin.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Getter;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.ilyasyoy.telegram.admin.EasyRandomFeatures;

class ParticipationTest implements EasyRandomFeatures {
    @Getter private EasyRandom easyRandom = new EasyRandom();

    @ParameterizedTest
    @CsvSource({
        "NOT_CONFIRMED,true",
        "CONFIRMATION_SENT,false",
        "CONFIRMED,false",
    })
    void testParticipation(Participation.State state, boolean result) {
        Participation randomParticipation = getRandomParticipation().withState(state);

        boolean requiresConfirmation = randomParticipation.requiresConfirmation();

        assertThat(requiresConfirmation).isEqualTo(result);
    }
}
