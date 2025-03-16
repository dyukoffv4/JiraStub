package dyukov.vladimir.jirastub;

import dyukov.vladimir.jirastub.dto.JiraStubIssue;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/2/issue")
public class JiraStubController {
    private final JiraStubDatabase database = new JiraStubDatabase();
    private final HttpHeaders localHeaders = new JiraStubHeaders();

    @PostMapping
    public ResponseEntity<String> createIssue(@RequestBody @Validated(JiraStubIssue.Create.class) JiraStubIssue request) {
        JiraStubIssue issue = database.insert(request);
        String response = String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}",
                issue.getExpand(), issue.getId(), issue.getKey(), issue.getSelf());
        return ResponseEntity.status(HttpStatus.CREATED).headers(localHeaders).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getIssue(@PathVariable String id) {
        JiraStubIssue issue = database.obtain(id);
        if (issue != null) return ResponseEntity.status(HttpStatus.OK).headers(localHeaders).body(issue.toString());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editIssue(@PathVariable String id, @RequestBody @Validated(JiraStubIssue.Update.class) JiraStubIssue request) {
        if (database.update(id, request)) return ResponseEntity.noContent().headers(localHeaders).build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable String id) {
        if (database.delete(id)) return ResponseEntity.noContent().headers(localHeaders).build();
        return ResponseEntity.notFound().build();
    }

    /// Обработка ошибок валидации в нужном формате
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> customErrorHandler(MethodArgumentNotValidException exception) {
        StringBuilder errors = new StringBuilder();
        for (ObjectError error : exception.getAllErrors()) {
            if (errors.isEmpty()) errors.append(error.getDefaultMessage());
            else errors.append(",").append(error.getDefaultMessage());
        }
        String response = String.format("{\"errorMessages\":[],\"errors\":{%s}}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
