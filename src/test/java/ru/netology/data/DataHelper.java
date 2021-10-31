package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {

    private DataHelper() {
    }


    @Value
    public static class CardNumber {
        String number;
    }

    public static CardNumber getValidCardNumber() {

        return new CardNumber("4444 4444 4444 4441");
    }

    public static CardNumber getNotValidCardNumber() {

        return new CardNumber("4444 4444 4444 4442");
    }

    @Value
    @Data
    public static class CardInfo {
        String month;
        int year;
        String name;
        String cvc;
    }

    public static CardInfo generateRandomCardInfo() {
        Faker faker = new Faker(new Locale("en"));
        return new CardInfo(
                String.valueOf(faker.number().numberBetween(8, 10)),
                faker.number().numberBetween(getYearCard(0), getYearCard(5)),
                faker.name().fullName(),
                faker.number().digits(3)
        );
    }

    public static int getYearCard(int yearInc){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        LocalDateTime date = LocalDateTime.now();
        String cardYear = date.plusYears(yearInc).format(formatter);
        return Integer.parseInt(cardYear);
    }



}
