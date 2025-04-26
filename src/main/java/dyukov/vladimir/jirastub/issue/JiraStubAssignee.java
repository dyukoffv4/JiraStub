package dyukov.vladimir.jirastub.issue;

import jakarta.validation.constraints.NotNull;

public class JiraStubAssignee {
    @NotNull(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class},
            message = "\"assignee\": \"expected Object containing a 'name' property\"")
    public String name;
}
