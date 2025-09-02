package io.github.tpalucki.revolut.loadbalancer.in;

import java.util.Optional;

public interface LoadBalancer {

    void registerServer(RegisterServerCommand command);

    Optional<ServerDetails> getServer(GetServerQuery query);
}
