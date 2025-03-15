package dyukov.vladimir.jirastub;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class JiraStubDTO {
    @NotNull(groups = {Create.class, Update.class}, message = "Fields should be")
    @Valid
    private Fields fields;

    @Data
    public static class Fields {
        @NotNull(groups = Create.class, message = "Summary should be")
        @NotBlank(groups = {Create.class, Update.class}, message = "Summary should be Real")
        private String summary;

        @NotNull(groups = Create.class, message = "Project should be")
        @Valid
        private Project project;

        @NotNull(groups = Create.class, message = "IssueType should be")
        @Valid
        private IssueType issuetype;
    }

    @Data
    public static class Project {
        private String id;
        private String key;

        @AssertTrue(groups = {Create.class, Update.class}, message = "Project ID or Key should be")
        public boolean isMyWillSatisfied() {
            if (id != null) return !id.isBlank();
            if (key != null) return !key.isBlank();
            return false;
        }
    }

    @Data
    public static class IssueType {
        @NotNull(groups = Create.class, message = "IssueType ID should be")
        @NotBlank(groups = {Create.class, Update.class}, message = "IssueType ID should be Real")
        @Pattern(regexp = "10000|10001|10100|10102", groups = {Create.class, Update.class}, message = "IssueType ID is wrong")
        private String id;
    }

    public interface Create {}
    public interface Update {}

    public String toString() {
        return String.format("{\"fields\":{\"summary\":\"%s\",\"project\":{\"id\":\"%s\",\"key\":\"%s\"},\"issue_type_id\":\"%s\"}}",
                fields.summary, fields.project.id, fields.project.key, fields.issuetype.id);
    }
}
