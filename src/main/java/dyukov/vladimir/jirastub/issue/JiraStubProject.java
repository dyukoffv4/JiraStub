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
}
