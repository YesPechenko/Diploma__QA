package ru.netology.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlDataHelper;
import ru.netology.page.FormPage;

import java.util.stream.Stream;

import static java.nio.channels.Selector.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PurchaseViaCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true));
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessPurchaseWithGenerateData()  {
        val FormPage = Selenide.open(System.getProperty("http://localhost:8080"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setRandomCart();
        byFormPage.checkSuccess();
        assertEquals(SqlDataHelper.getStatusLastTransaction(), "APPROVED");
    }



    public static Stream<DataHelper.CardInfo> getValidCardData() {
        return Stream.of(
                new DataHelper.CardInfo("06", DataHelper.getYearCard(1), "IVAN IVANOV", "691"),
                new DataHelper.CardInfo("03", DataHelper.getYearCard(1), "IVAN IVANOV", "999"),
                new DataHelper.CardInfo("12", DataHelper.getYearCard(0), "IVAN IVANOV", "691"),
                new DataHelper.CardInfo("07", DataHelper.getYearCard(0), "IVAN IVANOV", "333"));
    }

    @ParameterizedTest
    @MethodSource("getValidCardData")
    void shouldSuccessPurchaseWithData(DataHelper.CardInfo cardInfo)  {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setCardInfo(cardInfo);
        byFormPage.checkSuccess();
        assertEquals(SqlDataHelper.getStatusLastTransaction(), "APPROVED");
    }

    @Test
    void shouldErrorPurchaseWithNotValidNumberCard() {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setNotValidNumberCard();
        byFormPage.setCardInfo(new DataHelper.CardInfo("07", DataHelper.getYearCard(1), "IVAN IVANOV", "691"));
        byFormPage.checkError();
        assertEquals(SqlDataHelper.getStatusLastTransaction(), "DECLINED");
    }

    public static Stream<DataHelper.CardInfo> getNotValidCardDataForValidityErrorCard() {
        return Stream.of(
                new DataHelper.CardInfo("00", DataHelper.getYearCard(1), "IVAN IVANOV", "691"),
                new DataHelper.CardInfo("13", DataHelper.getYearCard(1), "IVAN IVANOV", "691"),
                new DataHelper.CardInfo("06", DataHelper.getYearCard(8), "IVAN IVANOV", "691"));
    }

    @ParameterizedTest
    @MethodSource("getNotValidCardDataForValidityCErrorard")
    void shouldErrorPurchaseWithDataValidityErrorCard(DataHelper.CardInfo cardInfo) {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setCardInfo(cardInfo);
        byFormPage.checkValidityErrorCard();
    }

    @Test
    void shouldErrorPurchaseWithDataValidityYearErrorCard() {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setCardInfo(new DataHelper.CardInfo("07", DataHelper.getYearCard(-1), "IVAN IVANOV", "691"));
        byFormPage.checkValidityErrorCard();
    }

    public static Stream<DataHelper.CardInfo> getNotValidCardDataForOwnerErrorCard() {
        return Stream.of(
                new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "ИВАН ИВАНОВ", "691"),
                new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "123", "691"),
                new DataHelper.CardInfo("05", DataHelper.getYearCard(1), "@$#3", "691"));
    }

    @ParameterizedTest
    @MethodSource("getNotValidCardDataForOwnerCardError")
    void shouldErrorPurchaseWithDataOwnerCardError(DataHelper.CardInfo cardInfo)  {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setCardInfo(cardInfo);
        byFormPage.checkValidityErrorCard();
    }

    @Test
    void shouldRightPriceInDb()  {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setRandomCart();
        byFormPage.checkSuccess();
        assertEquals(SqlDataHelper.getPriceLastTransaction(), "50000");
    }

    @Test
    void shouldRightPaymentIdInDb()  {
        val FormPage = Selenide.open(System.getProperty("sut.url"), FormPage.class);
        val byFormPage = FormPage.clickOnBuy();
        byFormPage.setValidNumberCard();
        byFormPage.setRandomCart();
        byFormPage.checkSuccess();
        assertEquals(SqlDataHelper.getPaymentId(), SqlDataHelper.getTransactionId());
    }
}
