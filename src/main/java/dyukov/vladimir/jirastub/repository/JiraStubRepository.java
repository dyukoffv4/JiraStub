package dyukov.vladimir.jirastub.repository;

import dyukov.vladimir.jirastub.issue.JiraStubIssue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class JiraStubRepository {
    private final ConcurrentHashMap<String, JiraStubIssue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    private final ObjectMapper mapper = new ObjectMapper();

    public String insert(JiraStubIssue issue) {
        String id = Long.toString(idCounter.getAndIncrement());
        issue.update(id, null);
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
        if (!issues.containsKey(id)) return false;
        issues.get(id).update(null, issue);
        return true;
    }

    public boolean delete(String id) {
        if (id.contains("-")) id = id.substring(id.lastIndexOf('-') + 1);
        return issues.remove(id) != null;
    }
}
