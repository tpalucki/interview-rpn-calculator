package io.github.tpalucki.revolut.loadbalancer;

import io.github.tpalucki.revolut.loadbalancer.in.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerServiceTest {

    private static final int DEFAULT_CAPACITY = 10;

    @Test
    void givenRegistryEmpty_whenServerRegisters_itBecomesAvailable() {
        var registry = createRegistry();
        var query = GetServerQuery.create();

        Optional<ServerDetails> serverOptional = registry.getServer(query);

        shouldBeEmpty(serverOptional);
    }

    @Test
    void givenRegistryEmpty_whenServerRegisters_thenItBecomesAvailable() {
        final var registry = createRegistry();
        var query = GetServerQuery.create();
        Optional<ServerDetails> serverOptional = registry.getServer(query);
        shouldBeEmpty(serverOptional);
        var registerCommand = new RegisterServerCommand("localhost");

        registry.registerServer(registerCommand);

        itShouldBeAvailable(registry, query);
    }

    @Test
    void givenRegistryIsFull_whenServerRegister_thenShouldThrowException() {
        final var registry = createRegistryWithCapacity(1);
        registerAServerWithAddress(registry, "1");

        assertThrows(IllegalStateException.class, () -> registerAServerWithAddress(registry, "2"));
    }

    @Test
    void whenMultipleServers_requestedTheyShouldDiffer() {
        //given
        var registry = createRegistryWithCapacity(10);

        registerAServerWithAddress(registry, "1");
        registerAServerWithAddress(registry, "2");
        registerAServerWithAddress(registry, "3");

        var query = GetServerQuery.create();

        // when
        var addresses = List.of(registry.getServer(query),
                        registry.getServer(query),
                        registry.getServer(query))
                .stream()
                .map(Optional::get)
                .map(ServerDetails::address)
                .toList();

//        org.assertj.core.api.Assertions.assertThat(addresses).containsExactly("1", "2", "3");
    }

    @Test
    void givenServerRegistered_whenSameServerRegister_thenShouldAllowOnlyUniqueServers() {
        //given
        final var registry = createRegistryWithCapacity(10);
        registerAServer(registry);

        // when/then
        var exception = assertThrows(IllegalArgumentException.class, () -> registerAServer(registry));
        assertEquals("Server already registered", exception.getMessage());
    }

    private static void registerAServerWithAddress(LoadBalancerService registry, String address) {
        var registerCommand = new RegisterServerCommand(address);
        registry.registerServer(registerCommand);
    }

    private static void registerAServer(LoadBalancerService registry) {
        registerAServerWithAddress(registry, "localhost");
    }

    private void itShouldBeAvailable(LoadBalancerService registry, GetServerQuery query) {
        registry.getServer(query)
                .ifPresentOrElse(
                        server -> assertEquals("localhost", server.address()),
                        () -> fail("Server should be available"));
    }

    private static void shouldBeEmpty(Optional<ServerDetails> server) {
        assertTrue(server.isEmpty());
    }

    private static LoadBalancerService createRegistry() {
        return new LoadBalancerService(DEFAULT_CAPACITY);
    }

    private static LoadBalancerService createRegistryWithCapacity(int capacity) {
        return new LoadBalancerService(capacity);
    }
}