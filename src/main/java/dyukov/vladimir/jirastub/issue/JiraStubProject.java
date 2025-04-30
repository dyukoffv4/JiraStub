package dyukov.vladimir.jirastub.issue;

import dyukov.vladimir.jirastub.issue.group.*;
import jakarta.validation.constraints.AssertFalse;

public class JiraStubProject {
    public String id;
    public String key;

    // Специальные валидаторы (всегда не пусто что-то одно)
    @AssertFalse(groups = Merged.class, message = "\"project\": \"project is required\"")
    private boolean isParamsNotCorrect() {
        return (id == null) ? ((key == null) || key.isBlank()) : id.isBlank();
    }

    // Дополнительный функционал
    public void update(JiraStubProject new_project) {
        if (new_project != null) {
            id = new_project.id;
            key = new_project.key;
        }
        if (id != null && key == null) key = "PROJECT-" + id;
        if (id == null) id = Integer.toString(key.hashCode(), 6);
    }
}
