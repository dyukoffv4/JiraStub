package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;

public class JiraStubFields {
    // Необязательные поля
    public String description;
    public List<String> labels;

    @Valid
    public JiraStubPriority priority;

    @Valid
    public JiraStubAssignee assignee;

    // Обязательные поля
    @NotNull(groups = Create.class, message = "\"summary\": \"Вы должны определить тему по запросу.\"")
    public String summary;

    @Valid @NotNull(groups = Create.class, message = "\"project\": \"project is required\"")
    public JiraStubProject project;

    @Valid @NotNull(groups = Create.class, message = "\"issuetype\": \"issue type is required\"")
    public JiraStubIssueType issuetype;

    // Специальные валидаторы (если не ноль то не пусто)
    @AssertFalse(groups = Merged.class, message = "\"summary\": \"Вы должны определить тему по запросу.\"")
    private boolean isSummaryBlank() {
        return (summary != null) && summary.isBlank();
    }

    // Дополнительный функционал
    public void update(JiraStubFields new_fields) {
        if (new_fields != null) {
            if (new_fields.summary != null) summary = new_fields.summary;
            if (new_fields.issuetype != null) issuetype = new_fields.issuetype;
            if (new_fields.project != null) project = new_fields.project;
            if (new_fields.description != null) description = new_fields.description;
            if (new_fields.assignee != null) assignee = new_fields.assignee;
            if (new_fields.priority != null) priority = new_fields.priority;
            if (priority != null) priority.update(new_fields.priority);
            if (project != null) project.update(new_fields.project);
        }
        else {
            if (priority != null) priority.update(null);
            if (project != null) project.update(null);
        }
        if (assignee != null && assignee.name.isBlank()) assignee = null;
        if (labels == null) labels = new ArrayList<>();
    }
}
