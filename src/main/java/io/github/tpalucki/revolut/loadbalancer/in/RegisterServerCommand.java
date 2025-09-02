package io.github.tpalucki.revolut.loadbalancer.in;

public record RegisterServerCommand(String address) {

    public RegisterServerCommand create(String address) {
        return new RegisterServerCommand(address);
    }
}
