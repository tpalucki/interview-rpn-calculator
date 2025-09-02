package io.github.tpalucki.revolut.loadbalancer;

import java.util.concurrent.atomic.AtomicInteger;

public record Server(String address, AtomicInteger load) {

}
