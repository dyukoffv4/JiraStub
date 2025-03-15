package dyukov.vladimir.jirastub.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Data
public class JiraStubFields {
    @NotNull(groups = JiraStubIssue.Create.class, message = "Summary should be")
    @NotBlank(groups = {JiraStubIssue.Create.class, JiraStubIssue.Update.class}, message = "Summary should be Real")
    private String summary;

    @NotNull(groups = JiraStubIssue.Create.class, message = "Project should be")
    @Valid
    private JiraStubProject project;

    @NotNull(groups = JiraStubIssue.Create.class, message = "IssueType should be")
    @Valid
    private JiraStubIssueType issuetype;

    private final Map<String, Object> additional = new HashMap<>();

    @JsonAnySetter
    public void addCustomField(String key, Object value) {
        this.additional.put(key, value);
    }

    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return String.format("{\"project\":%s,\"summary\":\"%s\",\"issuetype\":%s", project, summary, issuetype);
        }
    }
}
