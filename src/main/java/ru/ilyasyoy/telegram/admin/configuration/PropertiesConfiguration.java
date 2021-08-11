package ru.ilyasyoy.telegram.admin.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.ilyasyoy.telegram.admin.configuration.property.TelegramAdminBotProperties;

@Configuration
@EnableConfigurationProperties({
        TelegramAdminBotProperties.class
})
public class PropertiesConfiguration {
}
