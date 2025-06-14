package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;

public class JiraStubPriority {
    public static final List<String> priority_ids = List.of("1", "2", "3", "4", "5");
    public static final List<String> priority_names = List.of("Highest", "High", "Medium", "Low", "Lowest");

    public String id;
    public String name;

    // Специальные валидаторы (всегда не пусто, соответствует списку)
    @AssertTrue(groups = Merged.class, message = "\"priority\": \"Выбранный приоритет недействителен.\"")
    private boolean isParamsCorrect() {
        if (id != null) return !id.isBlank() && priority_ids.contains(id);
        if (name != null) return !name.isBlank() && priority_names.contains(name);
        return false;
    }

    // Дополнительный функционал
    public void update(JiraStubPriority new_priority) {
        if (new_priority != null) {
            id = new_priority.id;
            name = new_priority.name;
        }
        if (id != null) name = JiraStubPriority.priority_names.get(JiraStubPriority.priority_ids.indexOf(id));
        else id = JiraStubPriority.priority_ids.get(JiraStubPriority.priority_names.indexOf(name));
    }
}
