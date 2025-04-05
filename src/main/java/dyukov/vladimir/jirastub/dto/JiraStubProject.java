package dyukov.vladimir.jirastub.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class JiraStubProject {
    private String id;
    private String key;

    /// Проверяет наличие поля id. Если поле присутствует, пропускает проверку.
    /// Если поле отсутствует, проверяет наличие поля key. Если поле отсутствует, выдает ошибку.
    @AssertTrue(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "\"project\": \"project is required\"")
    private boolean isMyWillSatisfied() {
        if (id != null) return !id.isBlank();
        if (key != null) return !key.isBlank();
        return false;
    }
}
