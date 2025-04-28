package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.constraints.NotNull;

public class JiraStubAssignee {
    @NotNull(groups = Merged.class, message = "\"assignee\": \"expected Object containing a 'name' property\"")
    public String name;
}
