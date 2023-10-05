package by.saley.cryptobot.cryptobot.mapper;

import by.saley.cryptobot.cryptobot.entity.CurrencyEntity;
import by.saley.cryptobot.cryptobot.model.CurrencyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = ALWAYS)
public interface CurrencyMapper {

    CurrencyEntity mapToCurrencyEntity(CurrencyInfo currencyInfo);

    void updateCurrency(@MappingTarget CurrencyEntity currencyEntity, CurrencyInfo currencyInfo);
}
