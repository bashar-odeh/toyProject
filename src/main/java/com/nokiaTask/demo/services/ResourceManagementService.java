package com.nokiaTask.demo.services;

import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.repositories.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceManagementService {

    private ServerRepository serverRepository;

    static List<Server> server;
    private HashMap<String, Server> hashServer = new HashMap<>();

    public ResourceManagementService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        server = serverRepository.findAll();
//        if (server.size() == 0) {
//            Server s = new Server("default_server", 100);
//            server.add(s);
//            serverRepository.save(s);
//        }
    }

    public void assignServerToUser(String userId, double capacity) throws InterruptedException {
        Server s = this.occupyServer(capacity);
        synchronized (s) {
            if (s.getCapacity() < capacity) {
                assignServerToUser(userId, capacity);
                return;
            } else if (!s.isActive()) {
                assignServerToUser(userId, capacity);
                return;
            } else {
                System.out.println("capacity needs to be occupied " + capacity + " and server with id " + s.getId() + " has : " + s.getCapacity());
                s.setCapacity(s.getCapacity() - capacity);
                s.getUsers().add(userId);
                serverRepository.save(s);
                System.out.println("User : " + userId + " has granted : " + capacity + " from Server : " + s.getId() + " available : " + s.getCapacity());
                System.out.println("---------------------------------------------");
            }
        }
    }

    public Server occupyServer(double capacity) throws InterruptedException {

        for (Server s : server) {
            if (s.getCapacity() >= capacity) {
                return s;
            }
        }
        Server s = new Server(capacity + "" + Math.random(), 100);
        serverRepository.save(s);
        server.add(s);
        return s;
    }


}


