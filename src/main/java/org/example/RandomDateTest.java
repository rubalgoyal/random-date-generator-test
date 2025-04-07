package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RandomDateTest {
    private static WebDriver webDriver;
    private final String url = "https://codebeautify.org/generate-random-date";

    @BeforeAll
    public static void connect() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        webDriver = new ChromeDriver(chromeOptions);
    }

    @AfterAll
    public static void disconnect() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void enterDates(String start, String end) {
        webDriver.get(url);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("count")));

        webDriver.findElement(By.id("count")).clear();
        webDriver.findElement(By.id("count")).sendKeys("1"); // Get only 1 date

        webDriver.findElement(By.id("custom-format")).clear();
        webDriver.findElement(By.id("custom-format")).sendKeys("YY-MM-DD");

        webDriver.findElement(By.id("start")).clear();
        webDriver.findElement(By.id("start")).sendKeys(start);

        webDriver.findElement(By.id("end")).clear();
        webDriver.findElement(By.id("end")).sendKeys(end);
    }

    private String getOutput() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("generateRandomDate();");

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        WebElement output = wait.until(driver -> {
            WebElement el = driver.findElement(By.id("generatedRandomDateTextArea"));
            String text = el.getAttribute("value").trim(); // Get `value`, not `getText()`
            return text.isEmpty() ? null : el;
        });

        return output.getAttribute("value").trim();
    }

    private LocalDate getInputDatesFormatted(String input){
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, inputFormat);
    }

    private LocalDate getOutputFormatted(String input){
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return LocalDate.parse(input, outputFormat);
    }


    @Test
    public void testValidDateRange() {
        String start = "2020-01-01";
        String end = "2099-12-31";
        enterDates(start, end);

        String result = getOutput();
        System.out.println("Generated Date: " + result);

        LocalDate startDateFormat = getInputDatesFormatted(start);
        LocalDate endDateFormat = getInputDatesFormatted(end);
        LocalDate resultDateFormat = getOutputFormatted(result);

        boolean isBeforeOrEqual = startDateFormat.isBefore(resultDateFormat) || startDateFormat.isEqual(resultDateFormat);
        boolean isAfterOrEqual = endDateFormat.isAfter(resultDateFormat) || endDateFormat.isEqual(resultDateFormat);

        assertTrue(isBeforeOrEqual && isAfterOrEqual, String.valueOf(true));

    }

    @Test
    public void testLeapYear(){
        String start = "2020-02-29";
        String end =  "2020-02-29";
        enterDates(start, end);

        String result = getOutput();
        System.out.println("Generated Dates: " + result);
        LocalDate startDateFormat = getInputDatesFormatted(start);
        LocalDate endDateFormat = getInputDatesFormatted(end);
        LocalDate resultDateFormat = getOutputFormatted(result);

        boolean isBeforeOrEqual = startDateFormat.isBefore(resultDateFormat) || startDateFormat.isEqual(resultDateFormat);
        boolean isAfterOrEqual = endDateFormat.isAfter(resultDateFormat) || endDateFormat.isEqual(resultDateFormat);

        assertTrue(isBeforeOrEqual && isAfterOrEqual, String.valueOf(true));

    }

    @Test
    public void testEndDateBeforeStart(){
        String start = "2020-02-29";
        String end =  "2019-02-02";

        enterDates(start, end);

        String result = getOutput();
        System.out.println("Generated Dates: " + result);
        LocalDate startDateFormat = getInputDatesFormatted(start);
        LocalDate endDateFormat = getInputDatesFormatted(end);
        LocalDate resultDateFormat = getOutputFormatted(result);

        boolean isBeforeOrEqual = startDateFormat.isBefore(resultDateFormat) || startDateFormat.isEqual(resultDateFormat);
        boolean isAfterOrEqual = endDateFormat.isAfter(resultDateFormat) || endDateFormat.isEqual(resultDateFormat);

        assertTrue(isBeforeOrEqual && !isAfterOrEqual, String.valueOf(true));

    }

}
