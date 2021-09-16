package com.nokiaTask.demo.controllers;

import com.nokiaTask.demo.dao.AssignServer;
import com.nokiaTask.demo.documents.Server;
import com.nokiaTask.demo.services.ResourceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servers")
public class ServerController {
    private final ResourceManagementService resourceManagementService;

    /**
     * controls user requests
     *
     * @author BasharO
     */
//    @PostMapping("/assignServer")
//    public void assignServer(@RequestBody AssignServer assignServer) throws InterruptedException {
//        resourceManagementService.assignServerToUser(assignServer.getUserId(), assignServer.getCapacity());
//    }
    @PostMapping("/assign")
    public void assignServer(@RequestBody List<AssignServer> assignServers) throws InterruptedException {
        assignServers
                .stream()
                .forEach(assignServer -> {
                    try {
                        resourceManagementService.assignServerToUser(assignServer.getUserId(), assignServer.getCapacity());
                    } catch (Exception e) {
                    }
                });
    }

    @GetMapping("/active")
    public List<Server> getActiveServers() {
        return resourceManagementService.getActiveServers();
    }
}
