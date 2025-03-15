package dyukov.vladimir.jirastub.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class JiraStubProject {
    private String id;
    private String key;

    @AssertTrue(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "Project ID or Key should be")
    public boolean isMyWillSatisfied() {
        if (id != null) return !id.isBlank();
        if (key != null) return !key.isBlank();
        return false;
    }

    public String toString() {
        return String.format("{\"id\":\"%s\",\"key\":\"%s\"}", id, key);
    }
}
