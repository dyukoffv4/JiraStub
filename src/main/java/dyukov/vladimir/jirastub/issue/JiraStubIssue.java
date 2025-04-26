package dyukov.vladimir.jirastub.issue;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class JiraStubIssue {
    // Инициализация не изменяемых полей
    private final String expand = "renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations";

    // Необязательные или вычисляемые системой поля
    private String id;
    private String key;
    private String self;

    // Обязательные поля
    @NotNull(groups = {Create.class, Update.class}, message = "Внутренняя ошибка сервера")
    @Valid public JiraStubFields fields;

    // Интрефейсы для разграничения валидации по типу запроса
    public interface Create {}
    public interface Update {}

    /// Обновление полей, вычесляемых системой
    public void updateId(String new_id) {
        this.id = new_id;
        this.key = "TEST-" + new_id;
        this.self = "http://localhost/rest/api/2/issue/" + new_id;
    }

    /// Вывод мета информации об объекте в формате JSON
    public String toString() {
        return String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}", expand, id, key, self);
    }
}
