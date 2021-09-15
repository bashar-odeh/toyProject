//package com.nokiaTask.demo;
//
//import com.nokiaTask.demo.documents.Server;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class SyncExample {
//    List<Server> server;
//
//    public SyncExample() {
//        server = new ArrayList<>();
//
//    }
//
//    public void print() {
//        for (Server s : server) {
//            System.out.println("server users are " + s.getUsers());
//        }
//    }
//
////    public void func1(int userId, double capacity) {
////        boolean hasUserGranted = false;
////        synchronized (this) {
////            for (Server s : server) {
////                System.out.println("Working on server " + s.get_id());
////                if (!s.isActive()) continue;
////                System.out.println("User :" + userId + " requested :" + capacity + " from Server" + s.get_id() + " avilable : " + s.getCapacity());
////                if (s.getCapacity() >= capacity) {
////                    s.setCapacity(s.getCapacity() - capacity);
////                    System.out.println("User :" + userId + " has granted :" + capacity + " from Server" + s.get_id() + " avilable : " + s.getCapacity());
////                    if (s.getCapacity() == 0) {
////                        s.setActive(false);
////                    }
////                    hasUserGranted = true;
////                    s.getUsers().add(userId);
////                    break;
////                }
////            }
////            if (!hasUserGranted) {
////                System.out.println("Creating new server ... takes 20 seconds ");
////                Server newServer = new Server("1" + capacity, 100);
////                newServer.setCapacity(100 - capacity);
////                server.add(newServer);
////                newServer.getUsers().add(userId);
////                if (newServer.getCapacity() == 0) {
////                    newServer.setActive(false);
////                }
////                System.out.println("User :" + userId + " has granted :" + capacity + " from Server" + newServer.get_id() + " available : " + newServer.getCapacity());
////            }
////            System.out.println("---------------------------------------------");
////        }
////    }
//
//    public void assignServerToUser(int userId, double capacity) throws InterruptedException {
//        Server s = this.occupyServer(capacity);
//        synchronized (s) {
//            if (s.getCapacity() < capacity) assignServerToUser(userId, capacity);
//            if (!s.isActive()) return;
//            System.out.println("capacity needs to be occupied " + capacity + " and server with id " + s.get_id() + " has : " + s.getCapacity());
//            s.setCapacity(s.getCapacity() - capacity);
//            System.out.println("User : " + userId + " has granted : " + capacity + " from Server : " + s.get_id() + " available : " + s.getCapacity());
//            System.out.println("---------------------------------------------");
//        }
//    }
//
//    public Server occupyServer(double capacity) throws InterruptedException {
//        for (Server s : server) {
//            if (s.getCapacity() >= capacity) return s;
//        }
//        UUID uuid = UUID.randomUUID();
//        Server s = new Server( 100);
//        server.add(s);
//        return s;
//    }
//
//    public static void main(String[] args) {
//        SyncExample e1 = new SyncExample();
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(() -> {
//            try {
//                e1.assignServerToUser(1, 50);
//                e1.assignServerToUser(1, 10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}
