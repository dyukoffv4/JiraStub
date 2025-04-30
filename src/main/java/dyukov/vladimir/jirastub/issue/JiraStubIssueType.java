package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class JiraStubIssueType {
    @NotNull(groups = Merged.class, message = "\"issuetype\": \"issue type is required\"")
    @Pattern(regexp = "\\b10[012]0[012]\\b", groups = Merged.class, message = "\"issuetype\": \"valid issue type is required\"")
    public String id;
}
