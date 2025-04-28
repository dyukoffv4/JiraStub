package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertFalse;
import java.util.List;

public class JiraStubFields {
    // Необязательные поля
    public String description;
    public List<String> labels;

    @Valid public JiraStubPriority priority;
    @Valid public JiraStubAssignee assignee;

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
}
