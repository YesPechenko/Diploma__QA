package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlDataHelper;
import ru.netology.page.FormPage;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

        @Test
        @DisplayName("Должен быть успех покупки кредита с генерацией данных")
        void shouldSuccessCreditPurchaseWithGenerateData() {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setRandomCart();
            byFormPage.checkSuccess();
            assertEquals(SqlDataHelper.getStatusLastCreditTransaction(), "APPROVED");
        }


        public static Stream<DataHelper.CardInfo> getValidCardData() {
            return Stream.of(
                    new DataHelper.CardInfo("02", DataHelper.getYearCard(1), "IVAN IVANOV", "691"),
                    new DataHelper.CardInfo("04", DataHelper.getYearCard(1), "IVAN IVANOV", "040"),
                    new DataHelper.CardInfo("11", DataHelper.getYearCard(0), "IVAN IVANOV", "691"),
                    new DataHelper.CardInfo("09", DataHelper.getYearCard(0), "IVAN IVANOV", "999"));
        }

        @ParameterizedTest
//        @DisplayName("Валидные данные")
        @MethodSource("getValidCardData")
        void shouldSuccessCreditPurchaseWithData(DataHelper.CardInfo cardInfo) {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setCardInfo(cardInfo);
            byFormPage.checkSuccess();
            assertEquals(SqlDataHelper.getStatusLastCreditTransaction(), "APPROVED");
        }

        @Test
        @DisplayName("если ошибка кредитной покупки с недействительным номером, месяцем карты")
        void shouldErrorCreditPurchaseWithNotValidNumberCard() {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setNotValidNumberCard();
            byFormPage.setCardInfo(new DataHelper.CardInfo("07", DataHelper.getYearCard(1), "IVAN IVANOV", "691"));
            byFormPage.checkError();
            assertEquals(SqlDataHelper.getStatusLastCreditTransaction(), "DECLINED");
        }

        public static Stream<DataHelper.CardInfo> getNotValidCardDataForValidityCardError() {
            return Stream.of(
                    new DataHelper.CardInfo("00", DataHelper.getYearCard(1), "IVAN IVANOV", "691"),
                    new DataHelper.CardInfo("13", DataHelper.getYearCard(2), "IVAN IVANOV", "691"),
                    new DataHelper.CardInfo("07", DataHelper.getYearCard(9), "IVAN IVANOV", "691"));
        }

        @ParameterizedTest
//        @DisplayName("Должна быть ошибка кредитной покупки с ошибкой карты валидности данных")
        @MethodSource("getNotValidCardDataForValidityErrorCardData")
        void shouldErrorCreditPurchaseWithDataValidityCardError(DataHelper.CardInfo cardInfo) {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setCardInfo(cardInfo);
            byFormPage.checkValidityErrorCard();
        }

       @Test
       @DisplayName("должна быть ошибка кредитной покупки с ошибкой карты года действия данных, полем владельцем")
       void shouldErrorCreditPurchaseWithDataValidityYear() {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setCardInfo(new DataHelper.CardInfo("05", DataHelper.getYearCard(-1), "IVAN IVANOV", "691"));
            byFormPage.checkInputSubErrorCard();
        }

        public static Stream<DataHelper.CardInfo> getNotValidCardDataForOwnerCardError() {
            return Stream.of(
                    new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "ИВАН ИВАНОВ", "691"),
                    new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "123", "691"),
                    new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "@$#3", "691"));
        }

        @ParameterizedTest
//        @DisplayName("должна быть ошибка кредитной покупки с ошибкой карты владельца данных")
        @MethodSource("getNotValidCardDataForOwnerCardError")
        void shouldErrorCreditPurchaseWithDataOwnerCardError(DataHelper.CardInfo cardInfo) {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setCardInfo(cardInfo);
            byFormPage.checkInputSubErrorCard();
        }

        @Test
        @DisplayName("должен быть правильный кредитный идентификатор в базе данных")
        void shouldRightCreditIdInDb() {
            val FormPage = open(System.getProperty("sut.url"), FormPage.class);
            val byFormPage = FormPage.clickOnCredit();
            byFormPage.setValidNumberCard();
            byFormPage.setRandomCart();
            byFormPage.checkSuccess();
            assertEquals(SqlDataHelper.getCreditId(), SqlDataHelper.getIdInCreditTable());
        }

}


