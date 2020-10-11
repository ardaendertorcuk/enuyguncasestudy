package org.enuygun.methods;

import com.enuygun.helper.ElementHelper;
import com.enuygun.helper.StoreHelper;
import com.enuygun.model.ElementInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.time.Duration;
import java.util.List;

public class Methods {

    private Logger logger = LoggerFactory.getLogger(getClass());
    WebDriver driver;
    FluentWait<WebDriver> wait;
    long pollingEveryValue;

    public Methods(WebDriver driver){

        this.driver = driver;
        wait = setFluentWait(30);
    }

    public FluentWait<WebDriver> setFluentWait(long timeout){

        FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver);
        fluentWait.withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(pollingEveryValue))
                .ignoring(NoSuchElementException.class);
        return fluentWait;
    }

    public void waitByMilliSeconds(long milliSeconds){

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitBySeconds(long seconds){

        logger.info(seconds + " saniye bekleniyor...");
        waitByMilliSeconds(seconds*1000);
    }

    public ElementInfo getElementInfo(String key){

        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public By getBy(String key){

        logger.info("Element " + key + " değerinde tutuluyor");
        return ElementHelper.getElementInfoToBy(getElementInfo(key));
    }

    public WebElement findElement(String key){

        By by = getBy(key);
        logger.info("Element " + by.toString() + " by değerine sahip");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> findElements(String key){

        By by = getBy(key);
        logger.info("Element " + by.toString() + " by değerine sahip");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void ControlUrl(String Url){
        setFluentWait(30).until(ExpectedConditions.urlToBe(Url));
    }


    public void navigateToPage(String url){
        driver.navigate().to(url);
        ControlUrl(url);
    }

    public void sendKeys(String key, String input){
        By by = getBy(key);
        driver.findElement(by).sendKeys(input);
        waitBySeconds(2);
        driver.findElement(by).sendKeys(Keys.RETURN);
        logger.info(key + " elementine " + input + " değeri girildi.");
    }

    public void clickElement(String key){
        findElement(key).click();
        logger.info(key + " elementi üzerine tıklandı.");
    }

    public boolean isElementVisible(String key){

        try
        {
            wait.until(ExpectedConditions.visibilityOfElementLocated(getBy(key)));
            return true;
        }
        catch (Exception e)
        {
            logger.info("Element görünür değil");
            return false;
        }

    }

    public boolean isElementInvisible(String key){

        try
        {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(getBy(key)));
            return true;
        }
        catch (Exception e)
        {
            logger.info("Element görünüyor");
            return false;
        }

    }

    public boolean isElementVisible(String key, long timeout) {
        By by = getBy(key);
        logger.info(key + " elementi " + by.toString() + " by değerine sahip");
        FluentWait<WebDriver> fluentWait = setFluentWait(timeout);
        try {
            fluentWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.info(key + " elementi ekranda görünür değil!");
            return false;
        }
        logger.info(key + " elementi ekranda görünür!");
        return true;
    }

    private JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }

    public void scrollTo(int x, int y) {
        String script = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(script, true);
    }


    private Object executeJS(String script, boolean wait) {
        return wait ? getJSExecutor().executeScript(script, "") : getJSExecutor().executeAsyncScript(script, "");
    }


    public void keyValueChanger(String key, String content){
        ElementInfo element = getElementInfo(key);
        String value = element.getValue();
        String newValue = String.format(value,content);
        element.setValue(newValue);
    }

}
