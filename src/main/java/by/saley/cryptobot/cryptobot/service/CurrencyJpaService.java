package by.saley.cryptobot.cryptobot.service;

import by.saley.cryptobot.cryptobot.config.TelegramBotConfig;
import by.saley.cryptobot.cryptobot.entity.CurrencyEntity;
import by.saley.cryptobot.cryptobot.mapper.CurrencyMapper;
import by.saley.cryptobot.cryptobot.model.CurrencyInfo;
import by.saley.cryptobot.cryptobot.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyJpaService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final TelegramBotConfig telegramBotConfig;

    /**
     * Method that saves currencies and returns list of changed
     * @param currencies {@link List}<{@link CurrencyInfo}>
     * @return list of changed currencies {@link List}<{@link CurrencyInfo}>
     */
    @Transactional
    public List<CurrencyInfo> saveCurrencies(List<CurrencyInfo> currencies) {
        List<CurrencyInfo> changedCurrencies = new ArrayList<>();
        currencies.forEach(currencyInfo -> {
            CurrencyEntity currency;
            currency = currencyRepository.findBySymbol(currencyInfo.getSymbol());

            if (currency == null) {
                currency = currencyMapper.mapToCurrencyEntity(currencyInfo);
            } else {
                if (isCurrencyChanged(currency, currencyInfo)) {
                    changedCurrencies.add(currencyInfo);
                }

                currencyMapper.updateCurrency(currency, currencyInfo);
            }

            currencyRepository.save(currency);
        });

        return changedCurrencies;
    }

    private Boolean isCurrencyChanged(CurrencyEntity currencyEntity, CurrencyInfo currencyInfo) {
        BigDecimal gap = currencyInfo.getPrice().subtract(currencyEntity.getPrice());

        if(currencyEntity.getPrice().compareTo(BigDecimal.ZERO) == 0
                && currencyInfo.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        else if (currencyEntity.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }

        BigDecimal result = gap.divide(currencyEntity.getPrice(), 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100)).abs();

        return result.compareTo(new BigDecimal(telegramBotConfig.getPercent())) > 0;
    }

}
