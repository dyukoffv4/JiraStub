package dyukov.vladimir.jirastub.repository;

import dyukov.vladimir.jirastub.issue.JiraStubIssue;
import dyukov.vladimir.jirastub.issue.JiraStubPriority;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class JiraStubRepository {
    private final ConcurrentHashMap<String, JiraStubIssue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final ObjectMapper mapper = new ObjectMapper();

    public String insert(JiraStubIssue issue) {
        String id = Long.toString(idCounter.getAndIncrement());
        issue.updateId(id);

        if (issue.fields.assignee != null && issue.fields.assignee.name.isBlank()) issue.fields.assignee = null;
        if (issue.fields.labels == null) issue.fields.labels = new ArrayList<>();

        if (issue.fields.priority != null) {
            if (issue.fields.priority.id != null) {
                issue.fields.priority.name = JiraStubPriority.priority_names.get(JiraStubPriority.priority_ids.indexOf(issue.fields.priority.id));
            } else {
                issue.fields.priority.id = JiraStubPriority.priority_ids.get(JiraStubPriority.priority_names.indexOf(issue.fields.priority.name));
            }
        }

        if (issue.fields.project.id != null) { if (issue.fields.project.key == null) issue.fields.project.key = "PROJECT-" + issue.fields.project.id; }
        else issue.fields.project.id = Integer.toString(issue.fields.project.key.hashCode(), 6);

        issues.put(id, issue);
        return issue.toString();
    }

    public String obtain(String id) {
        if (id.contains("-")) id = id.substring(id.lastIndexOf('-') + 1);
        if (!issues.containsKey(id)) return null;

        try {
            return mapper.writeValueAsString(issues.get(id));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Issue serialize error");
        }
    }

    public boolean update(String id, JiraStubIssue issue) {
        if (id.contains("-")) id = id.substring(id.lastIndexOf('-') + 1);
        return issues.containsKey(id);

        // updater
    }

    public boolean delete(String id) {
        if (id.contains("-")) id = id.substring(id.lastIndexOf('-') + 1);
        return issues.remove(id) != null;
    }
}
