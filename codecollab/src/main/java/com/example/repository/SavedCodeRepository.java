package com.example.repository;

import com.example.codecollab.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.model.SavedCode;
import java.util.List;

public interface SavedCodeRepository extends MongoRepository<SavedCode, String> {
    List<SavedCode> findByUserEmail(String userEmail);
    boolean existsByUserEmailAndCodeName(String userEmail, String codeName);
}