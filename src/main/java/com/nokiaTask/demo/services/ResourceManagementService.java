package com.nokiaTask.demo.services;

import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.repositories.ServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manages user requests synchronously
 *
 * @author BasharO
 */
@Service
public class ResourceManagementService {

    private ServerRepository serverRepository;
    /**
     * store in it  all servers in Database
     */
    static List<Server> server;


    public ResourceManagementService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        serverRepository.deleteAll();
        serverRepository.save(new Server(Math.random() + "", 20));
        serverRepository.save(new Server(Math.random() + "", 20));
    }

    /**
     * assign server to user and
     * lock on server being used by a user so no user can access it  while it's being processed
     */
    public void assignServerToUser(String userId, double capacity) throws InterruptedException {
        Server s = this.getServer(capacity);
        synchronized (s) {
            if (isServerAvailbale(s, capacity) == null) {
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
     */
    public Server getServer(double capacity) throws InterruptedException {
        server = serverRepository.findAll();
        for (Server s : server) {
            Server tempServer = isServerAvailbale(s, capacity);
            if (tempServer != null) return tempServer;
        }
        return createServer();
    }

    private Server createServer() throws InterruptedException {

        Thread.sleep(2000);
        Server server = new Server("" + Math.random(), 100);
        serverRepository.save(server);
        return server;
    }

    /**
     * check whether server is available or not based on capacity and active status
     */
    private Server isServerAvailbale(Server s, double capacity) {
        if (s.getCapacity() >= capacity && s.isActive()) {
            return s;
        }
        return null;
    }

}


