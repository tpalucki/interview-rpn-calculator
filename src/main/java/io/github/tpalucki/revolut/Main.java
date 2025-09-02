package io.github.tpalucki.revolut;

import io.github.tpalucki.revolut.loadbalancer.LoadBalancerService;
import io.github.tpalucki.revolut.loadbalancer.in.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Load balancer is running...");
        LoadBalancer loadBalancer = new LoadBalancerService(10);

        loadBalancer.registerServer(new RegisterServerCommand("192.168.1.1"));
        loadBalancer.registerServer(new RegisterServerCommand("192.168.1.2"));
        loadBalancer.registerServer(new RegisterServerCommand("192.168.1.3"));

        IntStream.range(0, 1000)
                .parallel()
                .forEach(i -> CompletableFuture.runAsync(() -> loadBalancer.getServer(GetServerQuery.create())
                        .ifPresent(response -> System.out.println("Server address " + response.address())))
                );
    }
}