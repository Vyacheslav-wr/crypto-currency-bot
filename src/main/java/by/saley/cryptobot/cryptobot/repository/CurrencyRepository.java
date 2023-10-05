package by.saley.cryptobot.cryptobot.repository;

import by.saley.cryptobot.cryptobot.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

    CurrencyEntity findBySymbol(String symbol);
}
