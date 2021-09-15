package com.nokiaTask.demo.controllers;

import com.nokiaTask.demo.dao.AssignServer;
import com.nokiaTask.demo.services.ResourceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ServerController {
    private final ResourceManagementService resourceManagementService;

    /**
     * controls user requests
     * @author BasharO
     *
     */
    @PostMapping("/assignServer")
    public void assignServer(@RequestBody AssignServer assignServer) throws InterruptedException {
        resourceManagementService.assignServerToUser(assignServer.getUserId(), assignServer.getCapacity());
    }
}
