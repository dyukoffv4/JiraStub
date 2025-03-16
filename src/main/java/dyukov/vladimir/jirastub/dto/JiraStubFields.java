package dyukov.vladimir.jirastub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JiraStubFields {
    // Необязательные или вычисляемые системой поля
    private String description;
    private List<String> labels;
    @Valid private JiraStubPriority priority;
    @Valid private JiraStubAssignee assignee;

    // Обязательные поля
    @NotNull(groups = JiraStubIssue.Create.class, message = "Summary should be")
    @NotBlank(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "Summary should be Real")
    private String summary;

    @NotNull(groups = JiraStubIssue.Create.class, message = "Project should be")
    @Valid private JiraStubProject project;

    @NotNull(groups = JiraStubIssue.Create.class, message = "IssueType should be")
    @Valid private JiraStubIssueType issuetype;

    /// Обновление полей, вычесляемых системой
    public void update() {
        if (labels == null) labels = new ArrayList<>();
        if (assignee != null && assignee.getName().isBlank()) assignee = null;
        priority.update();
        project.update();
    }
}
