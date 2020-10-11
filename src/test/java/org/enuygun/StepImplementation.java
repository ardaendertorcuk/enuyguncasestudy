package org.enuygun;

import com.thoughtworks.gauge.Step;
import org.enuygun.driver.DriverCreator;
import org.enuygun.methods.Methods;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

public class StepImplementation extends DriverCreator {

    private static Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(StepImplementation.class);
    Methods methods;

    public StepImplementation() {

        methods = new Methods(driver);
    }

    @Step("<key> sayfasına git")
    public void navigateURL(String key) {
        methods.navigateToPage(key);
    }

    @Step("<element> alanına <input> değerini gir")
    public void sendKeys(String key, String input) {
        methods.sendKeys(key, input);
    }

    @Step("<element> üzerine tıkla")
    public void clickElement(String key) {
        methods.clickElement(key);
    }

    @Step("<saniye> saniye bekle")
    public void WaitBySeconds(long seconds) {
        methods.waitBySeconds(seconds);
    }

    @Step("<element> elementinin görünürlüğünü kontrol et")
    public void checkElementVisible(String key) {
        Assert.assertTrue(methods.isElementVisible(key));
    }

    @Step("<element> elementinin görünürlüğünü <seconds> saniye boyunca kontrol et")
    public void checkElementVisible(String key, long timeout) {
        Assert.assertTrue(methods.isElementVisible(key, timeout));
    }


    @Step("<element> alanında <date> tarihini seç")
    public void DatePick(String key, String content) {
        clickElement(key);
        methods.keyValueChanger("DatePickDay", content);
        logger.info("value değiştirildi");
        int i = 0;
        while (!methods.isElementVisible("DatePickDay", 2) && i < 12) {
            clickElement("SonrakiAyButon");
            i++;
        }
        clickElement("DatePickDay");
        logger.info("İstenilen tarihe tıklandı!");
    }


    @Step("<key> listesinin <index>. elemanına scroll yap ve tıkla")
    public void scrollToElementByIndexClick(String key,int index) {

        WebElement webElement = methods.findElements(key).get(index - 1);
        methods.scrollTo(webElement.getLocation().getX(),webElement.getLocation().getY());
        methods.waitBySeconds(2);
        webElement.click();
    }
}



