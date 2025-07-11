package com.example.codecolab.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "SavedCodes")
public class SavedCode {
    @Id
    private String id;

    private String userEmail;
    private String codeName;
    private String code;
    private String language;
    private Date createdAt = new Date();
    public void setCreatedAt(long timeMillis) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCreatedAt'");
    }
}
