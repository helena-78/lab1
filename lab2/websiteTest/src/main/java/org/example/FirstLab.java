package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirstLab {
    private WebDriver firefoxDriver;
    private static final String baseUrl = "https://www.nmu.org.ua/ua/";
    @BeforeClass(alwaysRun = true)
    public void setUp(){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-fullscreen");
        firefoxOptions.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.firefoxDriver = new FirefoxDriver(firefoxOptions);
    }
    @BeforeMethod
    public void preconditions(){
        firefoxDriver.get(baseUrl);
    }
    @AfterClass(alwaysRun = true)
    public void tearDown(){firefoxDriver.quit();}
    @Test
    public void testHeaderExists(){
        WebElement header = firefoxDriver.findElement(By.id("header"));
        Assert.assertNotNull(header);
    }
    @Test
    public void testClickOnForStudent(){
        WebElement forStudentButton = firefoxDriver.findElement(By.xpath("/html/body/center/div[4]/div/div[1]/ul/li[1]/a"));
        Assert.assertNotNull(forStudentButton);
        forStudentButton.click();
        Assert.assertNotEquals(firefoxDriver.getCurrentUrl(),baseUrl);
    }
    @Test
    public void testSlider(){
        WebElement nextButton = firefoxDriver.findElement(By.className("next"));
        WebElement nextButtonByCss = firefoxDriver.findElement(By.cssSelector("a.next"));
        Assert.assertEquals(nextButton, nextButtonByCss);
        WebElement previousButton = firefoxDriver.findElement(By.className("prev"));
        for(int i = 0; i < 20; i++){
            if(nextButton.getAttribute("class").contains("disabled")){
                previousButton.click();
                Assert.assertTrue(previousButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(nextButton.getAttribute("class").contains("disabled"));
            }
            else{
                nextButton.click();
                Assert.assertTrue(nextButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(previousButton.getAttribute("class").contains("disabled"));
            }
        }
    }
}

