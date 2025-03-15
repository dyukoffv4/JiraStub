package dyukov.vladimir.jirastub;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class JiraStubDTO {
    @NotNull(groups = {Create.class, Update.class})
    @Valid
    private Fields fields;

    @Data
    public static class Fields {
        @NotNull(groups = Create.class)
        @NotBlank(groups = {Create.class, Update.class})
        private String summary;

        @NotNull(groups = Create.class)
        @Valid
        private Project project;

        @NotNull(groups = Create.class)
        @Valid
        private IssueType issuetype;
    }

    @Data
    public static class Project {
        private String id;
        private String key;

        @AssertTrue(groups = Create.class)
        public boolean oneNotNull() {
            if (id != null) return !id.isBlank();
            if (key != null) return !key.isBlank();
            return false;
        }

        @AssertTrue(groups = Update.class)
        public boolean oneNotBlank() {
            if (id != null) return !id.isBlank();
            if (key != null) return !key.isBlank();
            return true;
        }
    }

    @Data
    public static class IssueType {
        @NotNull(groups = Create.class)
        @NotBlank(groups = {Create.class, Update.class})
        @Pattern(regexp = "10000|10001|10100|10102")
        private String id;
    }

    public interface Create {}
    public interface Update {}
}
