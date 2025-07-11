package com.example.codecolab;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import com.example.codecolab.model.SavedCode;
import com.example.codecolab.repository.SavedCodeRepository;

@SpringBootApplication
public class CodecollabApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodecollabApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(SavedCodeRepository repository) {
        return args -> {
           // ((Object) repository).createIndex();
            System.out.println("MongoDB index created");
        };
    }

    @Bean
    public AbstractMongoEventListener<SavedCode> savedCodeListener() {
        return new AbstractMongoEventListener<SavedCode>() {
            @Override
            public void onBeforeConvert(BeforeConvertEvent<SavedCode> event) {
                SavedCode savedCode = event.getSource();
                if (savedCode.getCreatedAt() == null) {
                    savedCode.setCreatedAt(System.currentTimeMillis());
                }
                System.out.println("Saving code: " + savedCode.getCodeName() + " for " + savedCode.getUserEmail());
            }
        };
    }
}