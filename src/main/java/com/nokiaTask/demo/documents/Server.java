package com.nokiaTask.demo.documents;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Data
@ToString
public class Server {
    @Id
    private String id;
    private double capacity = 100;
    private List<String> users;
    private boolean isActive = true;

    public Server(String id) {
        this.id = id;
        users = new ArrayList<>();
    }

}
