package com.example.codecolab.controller;


import com.example.codecolab.*;
import com.example.codecolab.model.SavedCode;
import com.example.codecolab.repository.SavedCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://coding-collaborator-ai-compiler.vercel.app"
})
public class CodeController {

    private final SavedCodeRepository repo;

    @PostMapping("/save-code")
    public ResponseEntity<?> saveCode(@RequestBody SavedCode code) {
        if (code.getUserEmail() == null || code.getCodeName() == null || code.getCode() == null || code.getLanguage() == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Missing required fields"));
        }
        String userEmail = code.getUserEmail().trim();
        String codeName = code.getCodeName().trim();
        if (repo.existsByUserEmailAndCodeName(userEmail, codeName)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Code name '" + codeName + "' already exists for user '" + userEmail + "'"));
        }
        code.setUserEmail(userEmail);
        code.setCodeName(codeName);
        code.setCode(code.getCode().trim());
        code.setLanguage(code.getLanguage().trim());
        repo.save(code);
        return ResponseEntity.ok(new SuccessResponse("Code saved successfully"));
    }

    @GetMapping("/saved-codes/{userEmail}")
public ResponseEntity<List<SavedCode>> getCodes(@PathVariable String userEmail) {
    if (userEmail == null || userEmail.trim().isEmpty()) {
        System.out.println("Invalid userEmail: " + userEmail);
        return ResponseEntity.badRequest().body(null);
    }
    List<SavedCode> codes = repo.findByUserEmail(userEmail.trim());
    System.out.println("Fetched codes for " + userEmail + ": " + codes.size());
    return ResponseEntity.ok(codes);
}

    @DeleteMapping("/delete-code/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable String id) {
        if (id == null || id.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid code ID"));
        }
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok(new SuccessResponse("Code deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new ErrorResponse("Code not found"));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(new SuccessResponse("OK", true));
    }

    // Helper classes for consistent JSON responses
    private static class SuccessResponse {
        private String message;
        private Boolean mongoConnected;

        public SuccessResponse(String message) {
            this.message = message;
            this.mongoConnected = null;
        }

        public SuccessResponse(String message, Boolean mongoConnected) {
            this.message = message;
            this.mongoConnected = mongoConnected;
        }

        public String getMessage() {
            return message;
        }

        public Boolean getMongoConnected() {
            return mongoConnected;
        }
    }

    private static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}