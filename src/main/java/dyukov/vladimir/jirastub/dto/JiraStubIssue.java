package dyukov.vladimir.jirastub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JiraStubIssue {
    @NotNull(groups = {Create.class, Update.class}, message = "Fields should be")
    @Valid
    private JiraStubFields fields;

    public interface Create {}
    public interface Update {}

    public String toString() {
        return String.format("\"fields\":%s", fields);
    }
}
