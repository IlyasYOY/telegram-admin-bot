package ru.ilyasyoy.telegram.admin.configuration.property;


import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;


@Validated
@ConstructorBinding
@ConfigurationProperties("telegram.admin.bot")
public record TelegramAdminBotProperties(
        @NotBlank String username,
        @NotBlank String token
) implements TelegramBotProperties {
    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
