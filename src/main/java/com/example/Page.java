package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.Closeable;
import java.util.List;

public class Page implements Closeable {

    protected static final String HOME_BUTTON_CLASS_NAME = "ring-menu__logo";
    private static final String ISSUES_PAGE_CLASS_NAME = "ring-menu__item__i";

    protected static final int TIMEOUT_IN_SECONDS = 10;
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public Page(WebDriver driver, String url) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT_IN_SECONDS);
        driver.get(url);
    }

    public Page(Page from) {
        this.driver = from.driver;
        this.wait = from.wait;
    }

    public List<WebElement> getElementsById(String id) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(id)));
        return driver.findElements(By.id(id));
    }

    public List<WebElement> getElementsByClassName(String className) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
        return driver.findElements(By.className(className));
    }

    public void moveHome() {
        getElementsByClassName(HOME_BUTTON_CLASS_NAME).get(0).click();
    }

    public void moveToIssues() {
        moveHome();
        WebElement issuesButton = getElementsByClassName(ISSUES_PAGE_CLASS_NAME).get(0);
        issuesButton.click();
    }

    @Override
    public void close() {
        driver.quit();
    }
}
