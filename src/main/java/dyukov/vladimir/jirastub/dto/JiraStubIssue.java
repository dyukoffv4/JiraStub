package dyukov.vladimir.jirastub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class JiraStubIssue {
    // Инициализация не изменяемых полей
    private final String expand = "renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations";

    // Необязательные или вычисляемые системой поля
    private String id;
    private String key;
    private String self;

    // Обязательные поля
    @NotNull(groups = {Create.class, Update.class}, message = "Внутренняя ошибка сервера")
    @Valid private JiraStubFields fields;

    // Интрефейсы для разграничения валидации по типу запроса
    public interface Create {}
    public interface Update {}

    /// Вывод мета информации объекта в формате JSON
    public String toShortString() {
        return String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}", expand, id, key, self);
    }

    /// Вывод объекта в формате JSON
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "Something went real wrong :(";
        }
    }

    /// Получение тестового Issue
    public static JiraStubIssue getTestIssue(String id_or_key) {
        String id = id_or_key.contains("-") ? id_or_key.substring(id_or_key.lastIndexOf("-")) : id_or_key;
        try { Long.parseLong(id); } catch (NumberFormatException e) { return null; }

        JiraStubFields fields = new JiraStubFields();
        fields.setSummary("JIRA STUB SUMMARY");
        fields.setDescription("JIRA STUB DESCRIPTION");

        JiraStubIssueType issueType = new JiraStubIssueType();
        issueType.setId("10001");
        fields.setIssuetype(issueType);

        JiraStubProject project = new JiraStubProject();
        project.setId("12345");
        project.setKey("TEST");
        fields.setProject(project);

        JiraStubIssue issue = new JiraStubIssue();
        issue.id = id;
        issue.key = id.equals(id_or_key) ? "TEST-" + id : id_or_key;
        issue.self = "http://localhost/rest/api/2/issue/" + id;
        issue.fields = fields;

        return issue;
    }
}
