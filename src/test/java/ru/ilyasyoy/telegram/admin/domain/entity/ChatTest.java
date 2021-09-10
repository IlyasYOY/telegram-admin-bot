package ru.ilyasyoy.telegram.admin.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChatTest {
    private static EasyRandom easyRandom = new EasyRandom();

    @ParameterizedTest
    @MethodSource("testActivateParams")
    void testActivate(Chat chat) {
        assertThat(chat.activate()).extracting(Chat::active).isEqualTo(true);
    }

    public static Stream<Arguments> testActivateParams() {
        return Stream.of(
                Arguments.of(
                        Chat.builder()
                                .active(false)
                                .telegramId(easyRandom.nextLong())
                                .name(easyRandom.nextObject(String.class))
                                .build()),
                Arguments.of(
                        Chat.builder()
                                .active(true)
                                .telegramId(easyRandom.nextLong())
                                .name(easyRandom.nextObject(String.class))
                                .build()));
    }

    @ParameterizedTest
    @MethodSource("testDeactivateParams")
    void testDeactivate(Chat chat) {
        assertThat(chat.deactivate()).extracting(Chat::active).isEqualTo(false);
    }

    public static Stream<Arguments> testDeactivateParams() {
        return Stream.of(
                Arguments.of(
                        Chat.builder()
                                .active(false)
                                .telegramId(easyRandom.nextLong())
                                .name(easyRandom.nextObject(String.class))
                                .build()),
                Arguments.of(
                        Chat.builder()
                                .active(true)
                                .telegramId(easyRandom.nextLong())
                                .name(easyRandom.nextObject(String.class))
                                .build()));
    }
}
