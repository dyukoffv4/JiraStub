package dyukov.vladimir.jirastub.dto;

import jakarta.validation.constraints.AssertTrue;

import lombok.Data;

import java.util.List;

@Data
public class JiraStubPriority {
    public static final List<String> priority_ids = List.of("1", "2", "3", "4", "5");
    public static final List<String> priority_names = List.of("Highest", "High", "Medium", "Low", "Lowest");

    private String id;
    private String name;

    /// Проверяет наличие поля id. Если поле присутствует, пропускает проверку.
    /// Если поле отсутствует, проверяет наличие поля key. Если поле отсутствует, выдает ошибку.
    /// Каждое поле проверяется на соответствие значениям из списков.
    @AssertTrue(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "\"priority\": \"Выбранный приоритет недействителен.\"")
    private boolean isMyWillSatisfied() {
        if (id != null) return !id.isBlank() && priority_ids.contains(id);
        if (name != null) return !name.isBlank() && priority_names.contains(name);
        return false;
    }
}
