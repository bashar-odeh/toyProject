package com.nokiaTask.demo.services;

import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.repositories.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Manages user requests synchronously
 *
 * @author BasharO
 */
@Service
public class ResourceManagementService {

    private ServerRepository serverRepository;
    /**
     * store in all servers in Database
     */
    static List<Server> server;
    private double maxCapacity = 100;

    public ResourceManagementService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        serverRepository.deleteAll();
    }

    /**
     * assign server to user
     * lock on server being used by a user so no user can access it  while it's being processed
     */
    public void assignServerToUser(String userId, double capacity) throws InterruptedException {
        if (capacity > maxCapacity) {
            return;
        }
        Server s = this.getServer(capacity);
        System.out.println("Server to be occupied" + s.getId() + " From user "+ userId);
        synchronized (s.getId().intern()) {
            if (isServerAvailbale(s, capacity) == false) {
                assignServerToUser(userId, capacity);
            }
            server = serverRepository.findAll();
            s.setCapacity(s.getCapacity() - capacity);
            s.getUsers().add(userId);
            serverRepository.save(s);
        }
    }

    /**
     * get available server for user if no server  then create one
     * @return Server
     */
    public Server getServer(double capacity) throws InterruptedException {
        server = serverRepository.findAll();
        Optional<Server> tempServer = server.stream().filter(server1 -> isServerAvailbale(server1, capacity)).findFirst();
        if (tempServer.isPresent()) {
            return tempServer.get();
        }
        return createServer();
    }

    /**
     * make sure that server is available based on capacity and active status
     * @return  Boolean
     */
    private boolean isServerAvailbale(Server s, double capacity) {
        if (s.getCapacity() >= capacity && s.isActive()) {
            return true;
        }
        return false;
    }

    /**
     * create server
     *
     * @return Server
     */
    private Server createServer() throws InterruptedException {
        Server s = new Server("" + Math.random(), 100);
        serverRepository.save(s);
        return s;
    }

    /**
     * Filter servers based on active status and return active list of active server
     *
     * @return List<Server>
     */

    public List<Server> getActiveServers() {
        return serverRepository.findAllByIsActiveTrue();
    }
}


