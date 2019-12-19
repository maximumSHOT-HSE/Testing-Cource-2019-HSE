package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends Page {

    private static final String LOGIN_ID = "id_l.L.login";
    private static final String PASSWORD_ID = "id_l.L.password";
    private static final String LOGIN_BUTTON_ID = "id_l.L.loginButton";

    public LoginPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public void login(String loginText, String passwordText) {
        WebElement login = getElementsById(LOGIN_ID).get(0);
        WebElement password = getElementsById(PASSWORD_ID).get(0);
        WebElement loginButton = getElementsById(LOGIN_BUTTON_ID).get(0);
        login.sendKeys(loginText);
        password.sendKeys(passwordText);
        loginButton.click();
    }
}
