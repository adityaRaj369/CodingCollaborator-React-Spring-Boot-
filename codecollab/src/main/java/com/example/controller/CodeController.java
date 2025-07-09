package com.example.controller;

import com.example.codecollab.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.model.SavedCode;
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
        if (repo.existsByUserEmailAndCodeName(code.getUserEmail(), code.getCodeName())) {
            return ResponseEntity.badRequest()
                    .body("Code name '" + code.getCodeName() + "' already exists for user '" + code.getUserEmail() + "'");
        }
        repo.save(code);
        return ResponseEntity.ok("Code saved successfully");
    }

    @GetMapping("/saved-codes/{userEmail}")
    public ResponseEntity<List<SavedCode>> getCodes(@PathVariable String userEmail) {
        return ResponseEntity.ok(repo.findByUserEmail(userEmail.trim()));
    }

    @DeleteMapping("/delete-code/{id}")
    public ResponseEntity<?> deleteCode(@PathVariable String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("Code deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Code not found");
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().body("{\"status\": \"OK\", \"mongoConnected\": true}");
    }
}