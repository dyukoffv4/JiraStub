package dyukov.vladimir.jirastub;

import dyukov.vladimir.jirastub.dto.JiraStubFields;
import dyukov.vladimir.jirastub.dto.JiraStubIssue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class JiraStubDatabase {
    private final Map<Long, JiraStubIssue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public JiraStubIssue insert(JiraStubIssue issue) {
        Long id = idCounter.getAndIncrement();
        issue.update(id.toString());
        issues.put(id, issue);
        return issue;
    }

    public JiraStubIssue obtain(Long id) {
        return issues.get(id);
    }

    public boolean update(Long id, JiraStubIssue issue) {
        if (!issues.containsKey(id)) return false;
        JiraStubFields fields = issues.get(id).getFields(), new_fields = issue.getFields();

        if (new_fields.getSummary() != null) fields.setSummary(new_fields.getSummary());
        if (new_fields.getIssuetype() != null) fields.setIssuetype(new_fields.getIssuetype());
        if (new_fields.getProject() != null) fields.setProject(new_fields.getProject());
        if (new_fields.getDescription() != null) fields.setDescription(new_fields.getDescription());
        if (new_fields.getAssignee() != null) fields.setAssignee(new_fields.getAssignee());
        if (new_fields.getPriority() != null) fields.setPriority(new_fields.getPriority());
        if (new_fields.getLabels() != null) fields.setLabels(new_fields.getLabels());

        return true;
    }

    public boolean delete(Long id) {
        return issues.remove(id) != null;
    }
}
