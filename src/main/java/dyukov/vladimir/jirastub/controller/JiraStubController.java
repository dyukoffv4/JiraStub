package dyukov.vladimir.jirastub.controller;

import dyukov.vladimir.jirastub.issue.JiraStubIssue;
import dyukov.vladimir.jirastub.repository.JiraStubRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/2/issue")
public class JiraStubController {
    private final JiraStubRepository repository = new JiraStubRepository();

    @PostMapping
    public ResponseEntity<?> createIssue(@RequestBody @Validated(JiraStubIssue.Create.class) JiraStubIssue request) {
        String response = repository.insert(request);
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIssue(@PathVariable String id) {
        String response = repository.obtain(id);
        if (response == null) return ResponseEntity.notFound().build();
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIssue(@PathVariable String id, @RequestBody @Validated(JiraStubIssue.Update.class) JiraStubIssue request) {
        if (!repository.update(id, request)) return ResponseEntity.notFound().build();
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable String id) {
        if (!repository.delete(id)) return ResponseEntity.notFound().build();
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> errorHandler(MethodArgumentNotValidException exception) {
        StringBuilder errors = new StringBuilder();
        for (ObjectError error : exception.getAllErrors()) {
            if (errors.isEmpty()) errors.append(error.getDefaultMessage());
            else errors.append(",").append(error.getDefaultMessage());
        }
        String response = String.format("{\"errorMessages\":[],\"errors\":{%s}}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
