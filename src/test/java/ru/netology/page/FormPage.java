package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class FormPage {

   // private static String host = System.getProperty("host");

    private SelenideElement heading = $(".heading");
    private static SelenideElement byButton = $$(".button__content").find(exactText("Купить"));
    private SelenideElement byCreditButton = $$(".button__content").find(exactText("Купить в кредит"));

    private static SelenideElement cardPayment = $$(".heading_theme_alfa-on-white").find(exactText("Оплата по карте"));
    private static SelenideElement creditAccordingToTheCard = $$(".heading_theme_alfa-on-white")
            .find(exactText("Кредит по данным карты"));


    public FormPage() {
        heading.shouldBe(visible);
    }

    public FormsPurchase clickOnBuy() {
        byButton.click();
        cardPayment.shouldBe(visible);
        return  new FormsPurchase();
    }

    public static FormsPurchase clickOnCredit() {
        byButton.click();
        creditAccordingToTheCard.shouldBe(visible);
        return  new FormsPurchase();
    }

}
