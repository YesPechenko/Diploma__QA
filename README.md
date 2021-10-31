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
 1. Сформировать отчеты:
    ```
     gradlew allureReport
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
1. Сформировать отчеты:
   ```
   gradlew allureReport
   ```