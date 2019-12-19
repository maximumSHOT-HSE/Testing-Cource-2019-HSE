package com.example;

import org.openqa.selenium.WebElement;

public class CreateIssuePage extends Page {

    private static final String EDIT_SUMMARY_CLASS_NAME = "edit-summary";
    private static final String EDIT_DESCRIPTION_CLASS_NAME = "edit-description";
    private static final String SUBMIT_BUTTON_CLASS_NAME = "submit-btn";
    private static final String CREATE_ISSUE_BUTTON_CLASS_NAME = "yt-header__create-btn";

    public CreateIssuePage(Page from) {
        super(from);
    }

    public void createIssue(String summaryText, String descriptionText) {
        moveHome();
        WebElement createIssueButton = getElementsByClassName(CREATE_ISSUE_BUTTON_CLASS_NAME).get(0);
        createIssueButton.click();
        getElementsByClassName(EDIT_SUMMARY_CLASS_NAME).get(0).sendKeys("");
        WebElement summaryElement = getElementsByClassName(EDIT_SUMMARY_CLASS_NAME).get(0);
        getElementsByClassName(EDIT_DESCRIPTION_CLASS_NAME).get(0).sendKeys("");
        WebElement descriptionElement = getElementsByClassName(EDIT_DESCRIPTION_CLASS_NAME).get(0);
        WebElement submitIssueButton = getElementsByClassName(SUBMIT_BUTTON_CLASS_NAME).get(0);
        summaryElement.sendKeys(summaryText);
        descriptionElement.sendKeys(descriptionText);
        submitIssueButton.click();
    }
}
