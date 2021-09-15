package com.nokiaTask.demo.services;

import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.repositories.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ResourceManagementService {

    private ServerRepository serverRepository;
    static List<Server> server;

    public ResourceManagementService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        server = serverRepository.findAll();
    }

    public void assignServerToUser(String userId, double capacity) throws InterruptedException {
        Server s = this.occupyServer(capacity);
        synchronized (s) {
            if (s.getCapacity() < capacity || !s.isActive()) {
                assignServerToUser(userId, capacity);
                return;
            } else {
                s.setCapacity(s.getCapacity() - capacity);
                s.getUsers().add(userId);
                serverRepository.save(s);
            }
        }
    }

  synchronized   public Server occupyServer(double capacity) throws InterruptedException {
        for (Server s : server) {
            if (s.getCapacity() >= capacity) {
                return s;
            }
        }
        Server s = new Server(capacity + "" + Math.random());
        serverRepository.save(s);
        return s;
    }


}


