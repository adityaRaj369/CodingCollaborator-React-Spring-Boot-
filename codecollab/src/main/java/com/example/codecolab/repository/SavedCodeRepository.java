package com.example.codecolab.repository;

import com.example.codecolab.model.SavedCode;
import com.example.codecolab.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SavedCodeRepository extends MongoRepository<SavedCode, String> {
    List<SavedCode> findByUserEmail(String userEmail);
    boolean existsByUserEmailAndCodeName(String userEmail, String codeName);
}