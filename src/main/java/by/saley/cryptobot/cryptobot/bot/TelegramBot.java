package by.saley.cryptobot.cryptobot.bot;

import by.saley.cryptobot.cryptobot.config.TelegramBotConfig;
import by.saley.cryptobot.cryptobot.model.CurrencyInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Telegram bot
 */
@Component
@AllArgsConstructor
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final List<Long> users = new ArrayList<>();

    private final TelegramBotConfig telegramBotConfig;

    /**
     * Method
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start" -> {
                    if (users.contains(chatId)) {
                        sendMessage(chatId, "You already subscribed");
                        return;
                    }

                    if(users.size() == telegramBotConfig.getCapacity()) {
                        sendMessage(chatId, "Sorry but our bot is not available now");
                    } else {
                        users.add(chatId);
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    }
                }
                case "/stop" -> {
                    users.remove(chatId);
                    sendMessage(chatId, "Thanks for usage");
                }
                default -> sendMessage(chatId, String.format("Sorry \"%s\" is unsupported command", message));
            }
        }
    }

    public void notifyUsers(List<CurrencyInfo> currencyInfos) {
        users.forEach(user -> sendMessage(user, currencyInfos.toString()));
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hello, " + name + ", nice to meet you, we glad that you started using out service";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Something went wrong with telegram api");
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfig.getToken();
    }
}
