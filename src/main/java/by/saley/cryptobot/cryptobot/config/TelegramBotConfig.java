package by.saley.cryptobot.cryptobot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Telegram bot config
 */
@EnableScheduling
@Data
@Configuration
public class TelegramBotConfig {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

    @Value("${currency.percent}")
    Integer percent;

    @Value("${users.capacity}")
    Integer capacity;

    @Bean
    public WebClient initWebClient(@Value("${currency.uri}") String uri) {
        return WebClient.builder()
                .baseUrl(uri)
                .build();
    }
}
