package com.example;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class IssuesPage extends Page {

    private static final String ISSUE_ID_ANCHOR_CLASS_NAME = "issueIdAnchor";
    private static final String BACK_BUTTON_ID = "id_l.I.tb.backToSearch";

    public IssuesPage(Page from) {
        super(from);
    }

    public List<Issue> getIssuePages() {
        moveToIssues();

        List<String> anchors = getElementsByClassName(ISSUE_ID_ANCHOR_CLASS_NAME).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        return anchors.stream()
                .map(s -> {
                    Issue issue = getIssuePageByAnchor(s);
                    getElementsById(BACK_BUTTON_ID).get(0).click();
                    return issue;
                })
                .collect(Collectors.toList());
    }

    private Issue getIssuePageByAnchor(String anchor) {
        moveToIssues();

        List<WebElement> issues = getElementsByClassName(ISSUE_ID_ANCHOR_CLASS_NAME);
        return issues.stream()
                .filter(element -> element.getText().equals(anchor))
                .findFirst()
                .map(element -> {
                    element.click();
                    return new Issue(this);
                }).get();
    }
}
