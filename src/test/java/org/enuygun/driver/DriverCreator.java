package org.enuygun.driver;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.ExecutionContext;

public class DriverCreator extends BaseDriver{

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static String osName;
    public static String browserName;
    protected static WebDriver driver;

    @BeforeSpec
    public void beforeSpec(ExecutionContext executionContext) {
        logger.info("=========================================================================" + "\r\n");
        logger.info("------------------------SPEC-------------------------");
        logger.info("SPEC FILE NAME: " + executionContext.getCurrentSpecification().getFileName());
        logger.info("SPEC NAME: " + executionContext.getCurrentSpecification().getName());
    }

    @BeforeScenario
    public void beforeScenario(ExecutionContext executionContext) {
        logger.info("_________________________________________________________________________" + "\r\n");
        logger.info("------------------------SCENARIO-------------------------");
        logger.info("SCENARIO NAME: " + executionContext.getCurrentScenario().getName());
        logger.info("SCENARIO TAG: " + executionContext.getCurrentScenario().getTags().toString());
        driver = getChromeDriver();
    }

    @AfterScenario
    public void quitDriver(){
        driver.quit();
    }
}
