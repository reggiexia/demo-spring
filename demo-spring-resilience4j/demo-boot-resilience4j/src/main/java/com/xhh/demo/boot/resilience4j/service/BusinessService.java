package com.xhh.demo.boot.resilience4j.service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public interface BusinessService {
    String failure();

    String success();

    String successException();

    String ignore();

    String failureWithFallback();

    Flux<String> fluxFailure();

    Mono<String> monoSuccess();

    Mono<String> monoFailure();

    Flux<String> fluxSuccess();

    CompletableFuture<String> futureSuccess();

    CompletableFuture<String> futureFailure();
}
