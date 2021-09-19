package com.nokiaTask.demo.documents;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDb document for storing servers data
 *
 * @author BasharO
 */

@Document
@Data
@ToString
public class Server {
    @Id
    private String id;
    private double capacity = 100;
    private List<String> users;
    private String status ;

    public Server(String id, double capacity,String status) {
        this.id = id;
        this.capacity = capacity;
        this.status = status;
        users = new ArrayList<>();
    }

}
