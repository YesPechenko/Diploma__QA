package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FormsPurchase {

    private static SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private static SelenideElement cardMonth = $(byText("Месяц")).parent().$(".input__control");
    private static SelenideElement cardYear = $(byText("Год")).parent().$(".input__control");
    private static SelenideElement cardName = $(byText("Владелец")).parent().$(".input__control");
    private static SelenideElement cardCVV = $(byText("CVC/CVV")).parent().$(".input__control");

    private static SelenideElement continueButton = $$(".button__content").find(exactText("Продолжить"));

    private SelenideElement success = $(withText("Успешно"));
    private SelenideElement error = $(withText("Ошибка"));
    private static SelenideElement successSearch = $$(".notification__title").find(exactText("Успешно"));
    private SelenideElement errorSearch = $$(".notification__title").find(exactText("Ошибка"));
    private static SelenideElement inputSub = $(".input__sub");

    public void  setValidNumberCard(){
        cardNumber.setValue(DataHelper.getValidCardNumber().getNumber());
    }

    public void setNotValidNumberCard(){
        cardNumber.setValue(DataHelper.getNotValidCardNumber().getNumber());
    }

    public void setRandomCart (){
        cardMonth.setValue(DataHelper.generateRandomCardInfo().getMonth());
        cardYear.setValue(String.valueOf(DataHelper.generateRandomCardInfo().getYear()));
        cardName.setValue(DataHelper.generateRandomCardInfo().getName());
        cardCVV.setValue(DataHelper.generateRandomCardInfo().getCvc());
        continueButton.click();
    }

    public void setCardInfo(DataHelper.CardInfo cardInfo) {
        cardMonth.setValue(cardInfo.getMonth());
        cardYear.setValue(String.valueOf(cardInfo.getYear()));
        cardName.setValue(cardInfo.getName());
        cardCVV.setValue(cardInfo.getCvc());
        continueButton.click();
    }

    public void checkSuccess() {
        success.shouldBe(visible, Duration.ofMillis(50000));
    }

    public void checkError() {
        error.shouldBe(visible, Duration.ofMillis(50000));
    }

    public void checkValidityErrorCard() {
        successSearch.shouldBe(visible);
    }

    public void checkValidityYearErrorCard() {
        errorSearch.shouldBe(visible);
    }

    public void checkInputSubErrorCard() {
        inputSub.shouldBe(visible);
    }
}
