package dyukov.vladimir.jirastub.issue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class JiraStubIssueType {
    @NotNull(groups = JiraStubIssue.Create.class, message = "\"issuetype\": \"issue type is required\"")
    @NotBlank(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class},
            message = "\"issuetype\": \"valid issue type is required\"")
    @Pattern(regexp = "10001|10100|10102", groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class},
            message = "\"issuetype\": \"valid issue type is required\"")
    public String id;
}
