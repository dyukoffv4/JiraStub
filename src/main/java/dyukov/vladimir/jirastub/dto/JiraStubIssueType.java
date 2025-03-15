package dyukov.vladimir.jirastub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class JiraStubIssueType {
    @NotNull(groups = JiraStubIssue.Create.class, message = "IssueType ID should be")
    @NotBlank(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "IssueType ID should be Real")
    @Pattern(regexp = "10000|10001|10100|10102", groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "IssueType ID is wrong")
    private String id;

    public String toString() {
        return String.format("{\"id\":\"%s\"}", id);
    }
}
