package com.example;

import java.util.Objects;

public class Issue {

    private static final String ISSUE_SUMMARY_ID = "id_l.I.ic.icr.it.issSum";
    private static final String DESCRIPTION_ID = "id_l.I.ic.icr.d.description";

    private final String summary;
    private final String description;

    public Issue(Page from) {
        summary = from.getElementsById(ISSUE_SUMMARY_ID).get(0).getText();
        description = from.getElementsById(DESCRIPTION_ID).get(0).getText();
    }

    public Issue(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return description.equals(issue.description) && summary.equals(issue.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, description);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
