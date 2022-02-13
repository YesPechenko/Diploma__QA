# Дипломный проект по профессии «Тестировщик»

### Запуск программы и тестов
#### Для работы с MySQL

1. Запуск контейнера MySQL:
    ```
    docker-compose up -d
    ```
1. Запуск SUT:
    ```
    java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
    ```
1. Запуск тестов в новом окне терминала:
    ```
    gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app
    ```   
1. Сформировать и открыть отчеты:
    ```
    gradlew allureReport
    gradlew allureServe
    ```
1. Остановить и удалить контейнеры
    ```
    docker-compose down
    ```

#### Для работы с Postgres
1. Запуск Postgres:
    ```
    docker-compose up -d
    ```
1. Запуск SUT:
    ```
    java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar
    ```
1. Запуск тестов в новом окне терминала:
    ```
    gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app
    ```   
1. Сформировать и открыть отчеты:
   ```
   gradlew allureReport
   gradlew allureServe
   ```
1. Остановить и удалить контейнеры
    ```
    docker-compose down
    ```
   
   ## Документы:
   [План тестирования приложения в веб-сервисе «Путешествие дня»](https://github.com/YesPechenko/Diploma__QA/blob/master/Plan.md)
   
   [Отчётные документы по итогам тестирования](https://github.com/YesPechenko/Diploma__QA/blob/master/Report.md)
   
   [Отчётные документы по итогам автоматизации](https://github.com/YesPechenko/Diploma__QA/blob/master/Summary.md)
   
