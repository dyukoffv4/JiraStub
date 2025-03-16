package dyukov.vladimir.jirastub.dto;

import dyukov.vladimir.jirastub.JiraStubSettings;

import jakarta.validation.constraints.AssertTrue;

import lombok.Data;

@Data
public class JiraStubPriority {
    private String id;
    private String name;

    /// Проверяет наличие поля id. Если поле присутствует, пропускает проверку.
    /// Если поле отсутствует, проверяет наличие поля key. Если поле отсутствует, выдает ошибку.
    /// Каждое поле проверяется на соответствие значениям из списков.
    @AssertTrue(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "Project ID or Name should be")
    private boolean isMyWillSatisfied() {
        if (id != null) return !id.isBlank() && JiraStubSettings.priority_ids.contains(id);
        if (name != null) return !name.isBlank() && JiraStubSettings.priority_names.contains(name);
        return false;
    }

    /// Обновление полей, вычесляемых системой
    public void update() {
        if (id != null) name = JiraStubSettings.priority_names.get(JiraStubSettings.priority_ids.indexOf(id));
        else id = JiraStubSettings.priority_ids.get(JiraStubSettings.priority_names.indexOf(name));
    }
}
