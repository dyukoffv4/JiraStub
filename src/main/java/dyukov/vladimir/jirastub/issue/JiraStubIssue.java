package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class JiraStubIssue {
    private final String expand = "renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations";

    // Вычисляемые системой поля
    private String id;
    private String key;
    private String self;

    // Обязательные поля
    @Valid @NotNull(groups = Merged.class, message = "Внутренняя ошибка сервера")
    public JiraStubFields fields;

    // Дополнительный функционал
    public void setId(String new_id) {
        id = new_id;
        key = "TEST-" + new_id;
        self = "http://localhost/rest/api/2/issue/" + new_id;
    }

    public void update(JiraStubIssue new_issue) {
        if (fields != null) fields.update(new_issue == null ? null : new_issue.fields);
    }

    @Override
    public String toString() {
        return String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}", expand, id, key, self);
    }
}
