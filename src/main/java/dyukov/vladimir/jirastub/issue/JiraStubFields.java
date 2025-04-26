package dyukov.vladimir.jirastub.issue;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class JiraStubFields {
    // Необязательные или вычисляемые системой поля
    public String description;
    public List<String> labels;
    @Valid public JiraStubPriority priority;
    @Valid public JiraStubAssignee assignee;

    // Обязательные поля
    @NotNull(groups = JiraStubIssue.Create.class, message = "\"summary\": \"Вы должны определить тему по запросу.\"")
    @NotBlank(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "\"summary\": \"Вы должны определить тему по запросу.\"")
    public String summary;

    @NotNull(groups = JiraStubIssue.Create.class, message = "\"project\": \"project is required\"")
    @Valid public JiraStubProject project;

    @NotNull(groups = JiraStubIssue.Create.class, message = "\"issuetype\": \"issue type is required\"")
    @Valid public JiraStubIssueType issuetype;
}
