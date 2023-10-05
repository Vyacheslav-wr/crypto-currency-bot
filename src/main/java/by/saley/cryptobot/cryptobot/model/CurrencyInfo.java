package by.saley.cryptobot.cryptobot.model;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CurrencyInfo {

    private String symbol;

    private BigDecimal price;
}
