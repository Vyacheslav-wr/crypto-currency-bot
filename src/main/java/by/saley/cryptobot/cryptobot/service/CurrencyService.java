package by.saley.cryptobot.cryptobot.service;

import by.saley.cryptobot.cryptobot.bot.TelegramBot;
import by.saley.cryptobot.cryptobot.model.CurrencyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

/**
 * Currency service
 */
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final WebClient webClient;

    private final CurrencyJpaService currencyJpaService;

    private final TelegramBot telegramBot;

    @Scheduled(cron = "${schedule.time}")
    public void getCurrencyRates() {
        WebClient.ResponseSpec rs = webClient.get()
                .uri("https://api.mexc.com/api/v3/ticker/price")
                .retrieve();
        CurrencyInfo[] currencyInfoList = rs.bodyToMono(CurrencyInfo[].class).block();
        List<CurrencyInfo> changedCurrencies = currencyJpaService.saveCurrencies(Arrays.asList(currencyInfoList));

        if (!changedCurrencies.isEmpty()) {
            notifyUser(changedCurrencies);
        }
    }

    public void notifyUser(List<CurrencyInfo> currencies) {
        telegramBot.notifyUsers(currencies);
    }
}
