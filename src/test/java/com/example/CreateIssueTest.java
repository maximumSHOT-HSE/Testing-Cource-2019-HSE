package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class CreateIssueTest {

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
    public void createLongIssue() {
        StringBuilder bsummary = new StringBuilder();
        StringBuilder bdescription = new StringBuilder();

        for (int i = 0; i < 5000; i++) {
            bsummary.append('x');
            bdescription.append('y');
        }

        String summary = bsummary.toString();
        String description = bdescription.toString();

        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();

        assertArrayEquals(
                Collections.singletonList(new Issue(summary, description)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createSimpleIssue() {
        String summary = "summary";
        String description = "description";
        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, description)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createSimpleIssueWithSpecificSymbolsAndSpaces() {
        String summary = "summarylrgerlg    kk      hjh     jh j213kj 32l1 0293@";
        String description = "123456   789 0-=!@    #$%^&*()_+qwertyuiop[]asdfghjklzxcvvnbv.b,v.b,\n:'`~!";
        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        String expectedSummary = summary.replaceAll("( ){2,}", " ");
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.singletonList(new Issue(expectedSummary, description)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(expectedSummary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptyDescription() {
        String summary = "empty description";
        String description = "";
        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, "No description")).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptySummary() {
        String summary = "";
        String description = "description";
        createIssuePage.createIssue(summary, description);
        createIssuePage.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("errorSeverity")));
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.emptyList().toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithEmptySummaryAndDescription() {
        String summary = "";
        String description = "";
        List<Issue> issuesBefore = issuesPage.getIssuePages();
        issuesBefore.sort(Comparator.comparing(Issue::getSummary).thenComparing(Issue::getDescription));

        createIssuePage.createIssue(summary, description);
        createIssuePage.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("errorSeverity")));
        createIssuePage.moveHome();

        List<Issue> issuesAfter = issuesPage.getIssuePages();
        issuesBefore.sort(Comparator.comparing(Issue::getSummary).thenComparing(Issue::getDescription));

        assertArrayEquals(issuesBefore.toArray(), issuesAfter.toArray());
    }

    @Test
    public void issueWithSpaceOnly() {
        String summary = " ";
        String description = "";

        createIssuePage.createIssue(summary, description);

        createIssuePage.moveHome();
        createIssuePage.moveHome();

        List<Issue> issues = issuesPage.getIssuePages();

        for (Issue issue : issues) {
            System.out.println(issue);
        }

        assertTrue(
                issues.stream()
                        .anyMatch(
                                issue -> issue.getSummary().isEmpty() &&
                                        issue.getDescription().equals("No description")
                        )
        );
    }

    @Test
    public void createIssueWithMultiLineSummary() {
        String multilineSummary =
                "one line\n" +
                        "second line\n" +
                        "third line\n";
        String singlelineSummary = "one linesecond linethird line";
        String description = "multiline summary";
        createIssuePage.createIssue(multilineSummary, description);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.singletonList(new Issue(singlelineSummary, description)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getDescription().equals(description))
                        .toArray()
        );
    }

    @Test
    public void createIssueWithMultiLineDescription() {
        String summary = "multiline description";
        String multilineDescription =
                "1 line\n" +
                        "2 line\n" +
                        "3 line";
        createIssuePage.createIssue(summary, multilineDescription);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Collections.singletonList(new Issue(summary, multilineDescription)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary)).toArray()
        );
    }

    @Test
    public void createTwoEqualIssues() {
        String summary = "repeated summary";
        String description = "repeated description";
        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        createIssuePage.createIssue(summary, description);
        createIssuePage.moveHome();
        List<Issue> issues = issuesPage.getIssuePages();
        assertArrayEquals(
                Arrays.asList(new Issue(summary, description), new Issue(summary, description)).toArray(),
                issues.stream()
                        .filter(issue -> issue.getSummary().equals(summary))
                        .toArray()
        );
    }
}
