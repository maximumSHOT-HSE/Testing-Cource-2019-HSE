package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class CreateIssueTest {

    private static final int TIMEOUT = 5000;

    private LoginPage loginPage;
    private CreateIssuePage createIssuePage;
    private IssuesPage issuesPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/WebDriver/bin/chromedriver");
        loginPage = new LoginPage(new ChromeDriver(), "http://localhost:8080/dashboard");
        loginPage.login("a", "a");
        createIssuePage = new CreateIssuePage(loginPage);
        issuesPage = new IssuesPage(loginPage);
    }

    @After
    public void tearDown() {
        loginPage.close();
    }

    @Test
    public void createSimpleIssue() throws InterruptedException {
        String summary = "summary";
        String description = "description";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, description)).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createSimpleIssueWithSpecificSymbolsAndSpaces() throws InterruptedException {
        String summary = "summarylrgerlg    kk      hjh     jh j213kj 32l1 0293@";
        String description = "123456   789 0-=!@    #$%^&*()_+qwertyuiop[]asdfghjklzxcvvnbv.b,v.b,\n:'`~!";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(TIMEOUT);
        String expectedSummary = summary.replaceAll("( ){2,}", " ");
        assertArrayEquals(
                Collections.singletonList(new Issue(expectedSummary, description)).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(expectedSummary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptyDescription() throws InterruptedException {
        String summary = "empty description";
        String description = "";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, "No description")).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptySummary() throws InterruptedException {
        String summary = "";
        String description = "description";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.emptyList().toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptySummaryAndDescription() throws InterruptedException {
        String summary = "";
        String description = "";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.emptyList().toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithMultiLineSummary() throws InterruptedException {
        String multilineSummary =
                "one line\n" +
                        "second line\n" +
                        "third line\n";
        String singlelineSummary = "one linesecond linethird line";
        String description = "multiline summary";
        createIssuePage.createIssue(multilineSummary, description);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.singletonList(new Issue(singlelineSummary, description)).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getDescription().equals(description))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithMultiLineDescription() throws InterruptedException {
        String summary = "multiline description";
        String multilineDescription =
                "1 line\n" +
                        "2 line\n" +
                        "3 line";
        createIssuePage.createIssue(summary, multilineDescription);
        Thread.sleep(TIMEOUT);
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, multilineDescription)).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary)).toArray()
        );
    }

    @Test
    public void createTwoEqualIssues() throws InterruptedException {
        String summary = "repeated summary";
        String description = "repeated description";
        createIssuePage.createIssue(summary, description);
        Thread.sleep(2000);
        createIssuePage.createIssue(summary, description);
        Thread.sleep(2000);
        assertArrayEquals(
                Arrays.asList(new Issue(summary, description), new Issue(summary, description)).toArray(),
                issuesPage.getIssuePages()
                        .stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }
}
