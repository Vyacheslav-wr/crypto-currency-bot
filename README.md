# Crypto currency bot

The aim of this project is to supply user with telegram bot that will notify him of changes 
on crypto currency market.

# Used libraries
    1. Spring Data Jpa
    2. Liquibase
    3. Lombok
    4. TelegramBots
    5. Mapstruct

# Build and run requirements
    1. Java 17
    2. PostgreSql 14
    3. Gradle

1. To build project you need to execute ```./gradlew build``` in project folder.
2. You should create database on your system named crypto
3. TO run project just run ```java -jar ./build/libs/cryptobot-0.0.1-SNAPSHOT.jar```

# Bot usage
1. Open bot https://t.me/crypto1_test1_bot
2. /start - to start getting messages from bot
3. /stop - stop getting messages from bot

# For the future updates
It will be a good idea to save program state (for example subscribed users) 