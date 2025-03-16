package dyukov.vladimir.jirastub.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JiraStubAssignee {
    @NotNull(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "Assignee Name should be")
    private String name;
}
