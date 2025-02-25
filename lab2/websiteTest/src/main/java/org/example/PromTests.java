package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PromTests {
    private WebDriver driver;
    private final String baseUrl = "https://prom.ua/";

    @BeforeClass(alwaysRun = true)
    public void setup() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.setImplicitWaitTimeout(Duration.ofSeconds(15));
        this.driver = new FirefoxDriver(options);
    }

    @BeforeMethod
    public void navigateToHomePage() {
        driver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testClickOnElement() {
        WebElement menuItem = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/div/div[2]/ul/li[1]"));
        Assert.assertNotNull(menuItem, "Menu item should not be null");
        menuItem.click();
        Assert.assertNotEquals(driver.getCurrentUrl(), baseUrl, "URL should change after clicking the menu item");
    }
    @Test
    public void testSearchFieldOnMainPage(){
        driver.get(baseUrl);
        WebElement searchField = driver.findElement(By.tagName("input"));
        Assert.assertNotNull(searchField);
        System.out.println(String.format("Name attribute: %s", searchField.getAttribute("name")) +
                String.format("\nID attribute: %s", searchField.getAttribute("id")) +
                String.format("\nType attribute: %s", searchField.getAttribute("type")) +
                String.format("\nValue attribute: %s", searchField.getAttribute("value")) +
                String.format("\nPosition: (%d;%d)", searchField.getLocation().x, searchField.getLocation().y) +
                String.format("\nSize: %dx%d", searchField.getSize().height, searchField.getSize().width)
        );
        String inputValue = "phone";
        searchField.sendKeys(inputValue);
        Assert.assertEquals(searchField.getAttribute("value"), inputValue);
        searchField.sendKeys(Keys.ENTER);
        Assert.assertNotEquals(driver.getCurrentUrl(), baseUrl);
    }
    @Test
    public void testNextButton(){
        WebElement nextButton = driver.findElement(By.className("L0VTc_8-zJW"));
        WebElement nextButtonByCss = driver.findElement(By.cssSelector("div.L0VTc_8-zJW"));
        Assert.assertEquals(nextButton,nextButtonByCss);
        WebElement previousButton = driver.findElement(By.className("_3uXYG_8-zJW _8BNAe"));
        for(int i = 0; i < 10; i++){
            if(nextButton.getAttribute("class").contains("disabled")) {
                previousButton.click();
                Assert.assertTrue(previousButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(nextButton.getAttribute("class").contains("disabled"));
            }
            else {
                nextButton.click();
                Assert.assertTrue(nextButton.getAttribute("class").contains("disabled"));
                Assert.assertFalse(previousButton.getAttribute("class").contains("disabled"));
            }
        }
    }
}
