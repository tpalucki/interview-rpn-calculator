package io.github.tpalucki.revolut.loadbalancer.in;

public record GetServerQuery() {

    public static GetServerQuery create() {
        return new GetServerQuery();
    }
}
