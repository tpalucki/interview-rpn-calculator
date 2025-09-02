package io.github.tpalucki.revolut.loadbalancer;

import io.github.tpalucki.revolut.loadbalancer.in.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancerService implements LoadBalancer {

    private final Integer maxCapacity;
    private final Map<String, AtomicInteger> registryStore;

    public LoadBalancerService(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.registryStore = new HashMap<>();
    }

    @Override
    public void registerServer(RegisterServerCommand command) {
        if (this.registryStore.containsKey(command.address())) {
            throw new IllegalArgumentException("Server already registered");
        }
        if (this.registryStore.size() < this.maxCapacity) {
            this.registryStore.computeIfAbsent(command.address(), addr -> new AtomicInteger(0));
        } else {
            throw new IllegalStateException("Registry is full");
        }
    }

    @Override
    public Optional<ServerDetails> getServer(GetServerQuery query) {
        if (this.registryStore.isEmpty()) {
            return Optional.empty();
        }

        // find random
//            var random = ThreadLocalRandom.current();
//            int index = random.nextInt(0, this.registryStore.size());
//            final Map.Entry<String, AtomicInteger> entry = this.registryStore.entrySet()
//                    .stream()
//                    .toList().get(index);
//            entry.getValue().incrementAndGet();
//            return Optional.of(new ServerDetails(entry.getKey()));

        // find lowest load
        Map.Entry<String, AtomicInteger> first = Collections.synchronizedSet(this.registryStore.entrySet())
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().get()))
                .toList()
                .getFirst();
        first.getValue().getAndIncrement();
        return Optional.of(first)
                .map(Map.Entry::getKey)
                .map(ServerDetails::new);
    }
}
