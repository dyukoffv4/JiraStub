package dyukov.vladimir.jirastub;

import dyukov.vladimir.jirastub.dto.JiraStubIssue;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/rest/api/2/issue")
public class JiraStubController {
    private final Map<Long, JiraStubIssue> issues = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(100000);
    private final HttpHeaders localHeaders = new JiraStubHeaders();

    @PostMapping
    public ResponseEntity<String> createIssue(@RequestBody @Validated(JiraStubIssue.Create.class) JiraStubIssue request) {
        Long id = idCounter.getAndIncrement();
        request.update(id.toString());
        issues.put(id, request);
        String response = String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}",
                request.getExpand(), request.getId(), request.getKey(), request.getSelf());
        return ResponseEntity.status(HttpStatus.CREATED).headers(localHeaders).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getIssue(@PathVariable Long id) {
        if (issues.containsKey(id)) return ResponseEntity.status(HttpStatus.OK).headers(localHeaders).body(issues.get(id).toString());
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editIssue(@PathVariable Long id, @RequestBody @Validated(JiraStubIssue.Update.class) JiraStubIssue request) {
        if (issues.containsKey(id)) return ResponseEntity.noContent().headers(localHeaders).build();
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable Long id) {
        if (issues.remove(id) != null) return ResponseEntity.noContent().headers(localHeaders).build();
        return ResponseEntity.notFound().build();
    }

    /// Обработка ошибок валидации в нужном формате
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> customErrorHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getAllErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
        String response = String.format("{\"errorMessages\":[%s],\"errors\":{%s}}", "", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
