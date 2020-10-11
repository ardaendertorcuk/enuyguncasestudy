package org.enuygun.driver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BaseDriver {

    public ChromeDriver getChromeDriver(){
        DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
        //capabilities
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("test-type");
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.addArguments("ignore-certificate-errors");
        chromeOptions.addArguments("disable-translate");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("disable-plugins");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("Zoom-0.5");
        chromeOptions.merge(capabilities);
        System.setProperty("webdriver.chrome.driver","drivers/chrome/chromedriver.exe");
        return new ChromeDriver(chromeOptions);
    }
}
