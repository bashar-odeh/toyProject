package com.nokiaTask.demo.services;

import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.repositories.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceManagementService {

    private ServerRepository serverRepository;
    static List<Server> server;

    public ResourceManagementService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        serverRepository.save(new Server(Math.random() + "", 20));
        serverRepository.save(new Server(Math.random() + "", 20));
        server = serverRepository.findAll();

    }

    public void assignServerToUser(String userId, double capacity) throws InterruptedException {
        Server s = this.getServer(capacity);

        synchronized (s) {
            if (s.getCapacity() < capacity || !s.isActive()) {
                assignServerToUser(userId, capacity);
                return;
            }
            s.setCapacity(s.getCapacity() - capacity);
            s.getUsers().add(userId);
            serverRepository.save(s);
            
        }
    }

    public Server getServer(double capacity) throws InterruptedException {
        server = serverRepository.findAll();
        for (Server s : server) {
            if (s.getCapacity() >= capacity) {
                return s;
            }
        }
        Server s = new Server("" + Math.random(), 100);
        serverRepository.save(s);
        return s;
    }


}


