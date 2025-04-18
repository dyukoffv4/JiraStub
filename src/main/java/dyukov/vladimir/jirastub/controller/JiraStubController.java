package dyukov.vladimir.jirastub.controller;

import dyukov.vladimir.jirastub.dto.JiraStubIssue;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/2/issue")
public class JiraStubController {

    @PostMapping
    public ResponseEntity<?> createIssue(@RequestBody @Validated(JiraStubIssue.Create.class) JiraStubIssue request) {
        String id = Long.toString((long) (Math.random() * 1000));
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.status(HttpStatus.CREATED).body(getShortResponse(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIssue(@PathVariable String id) {
        JiraStubIssue issue = JiraStubIssue.getTestIssue(id);
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.status(HttpStatus.OK).body(issue.toString());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIssue(@PathVariable String id, @RequestBody @Validated(JiraStubIssue.Update.class) JiraStubIssue request) {
        try { Thread.sleep(50); } catch (InterruptedException e) { System.err.println(e.toString()); }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable String id) {
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

    private static String getShortResponse(String id) {
        String expand = "renderedFields,names,schema,operations,editmeta,changelog,versionedRepresentations";
        String key = "TEST-" + id;
        String self = "http://localhost/rest/api/2/issue/" + id;
        return String.format("{\"expand\":\"%s\",\"id\":\"%s\",\"key\":\"%s\",\"self\":\"%s\"}", expand, id, key, self);
    }
}
