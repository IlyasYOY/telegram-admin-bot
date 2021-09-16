package ru.ilyasyoy.telegram.admin;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jeasy.random.EasyRandom;
import ru.ilyasyoy.telegram.admin.domain.entity.Chat;
import ru.ilyasyoy.telegram.admin.domain.entity.Participation;
import ru.ilyasyoy.telegram.admin.domain.entity.User;

public interface EasyRandomFeatures {
    EasyRandom getEasyRandom();

    default User getRandomUser() {
        EasyRandom easyRandom = getEasyRandom();

        long telegramId = easyRandom.nextLong();
        String name = easyRandom.nextObject(String.class);

        return User.builder().telegramId(telegramId).username(name).build();
    }

    default Stream<User> getRandomUsers(int size) {
        return IntStream.range(0, size).mapToObj(x -> getRandomUser());
    }

    default Participation getRandomParticipation() {
        EasyRandom easyRandom = getEasyRandom();

        var chat = new Chat(easyRandom.nextLong(), easyRandom.nextObject(String.class));
        var user = new User(easyRandom.nextLong(), easyRandom.nextObject(String.class));
        var state = easyRandom.nextObject(Participation.State.class);

        return Participation.builder().chat(chat).user(user).state(state).build();
    }

    default Stream<Participation> getRandomParticipations(int size) {
        return IntStream.range(0, size).mapToObj(x -> getRandomParticipation());
    }

    default Chat getRandomChat() {
        EasyRandom easyRandom = getEasyRandom();

        long telegramId = easyRandom.nextLong();
        String name = easyRandom.nextObject(String.class);

        return new Chat(telegramId, name);
    }

    default Stream<Chat> getRandomChats(int size) {
        return IntStream.range(0, size).mapToObj(x -> getRandomChat());
    }
}
