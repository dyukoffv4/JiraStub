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

    /// Обновление полей, вычесляемых системой
    public void update(String id) {
        this.id = id;
        key = "TEST-" + id;
        self = "http://localhost/rest/api/2/issue/" + id;
        fields.update();
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
}
