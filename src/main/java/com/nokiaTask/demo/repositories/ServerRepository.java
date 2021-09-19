package com.nokiaTask.demo.repositories;

import com.nokiaTask.demo.documents.Server;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServerRepository   extends MongoRepository<Server, String> {
    List<Server>  findServerByStatus(String status);
}
