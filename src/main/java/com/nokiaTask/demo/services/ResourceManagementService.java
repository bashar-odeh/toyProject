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
     * store in all active servers in Database
     */
    static List<Server> server;
    /**
     * store in all being created servers in Database
     */
    static List<Server> serverCreating;
    private double maxCapacity = 100;
    private static Object serverIsBeingCreated = new Object();

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
        synchronized (this) {
            Server s = this.getServer(capacity);
            if (isServerAvailbale(s, capacity) == false) {
                assignServerToUser(userId, capacity);
                return;
            }
            s.setCapacity(s.getCapacity() - capacity);
            s.getUsers().add(userId);
            serverRepository.save(s);
            server = serverRepository.findAll();
        }
    }

    /**
     * get available server for user if no server  then create one
     *
     * @return Server
     */
    public Server getServer(double capacity) throws InterruptedException {
        server = serverRepository.findServerByStatus("active");
        Optional<Server> tempServer = server.stream().filter(server1 -> isServerAvailbale(server1, capacity)).findFirst();
        if (tempServer.isPresent()) {
            return tempServer.get();
        }
        return createServer();
    }

    /**
     * make sure that server is available based on capacity and active status
     *
     * @return Boolean
     */
    private boolean isServerAvailbale(Server s, double capacity) {
        if (s.getCapacity() >= capacity && s.getStatus().equalsIgnoreCase("active")) {
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
        Thread.sleep(2000);
        Server s = new Server("" + Math.random(), 100, "creating");
        s.setStatus("active");
        serverRepository.save(s);
        return s;
    }

    /**
     * Filter servers based on active status and return active list of active server
     *
     * @return List<Server>
     */
    public List<Server> getActiveServers(String status) {
        return serverRepository.findServerByStatus(status);
    }
}


