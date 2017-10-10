package com.timadair.reactivewebdemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

/**
 * Demonstrates the flexibility of Spring Web.
 *
 * @author timadair
 * Created 10/10/17.
 */
@RestController
public class GetController {
  @GetMapping(value = "/list")
  public List<String> getList() {
    return Flux.interval(Duration.ofMillis(50))
      .take(100) // Take unsubscribes after the 100th event.
      .map(i -> "count: " + i)
      .collectList().block(); // This blocks until the take calls onCompleted on its subscribers.
  }

  @GetMapping(value = "/flux", produces = "text/event-stream")
  public Flux<String> getFlux() {
    return Flux.interval(Duration.ofMillis(50))
      .take(100) // Take unsubscribes after the 100th event.
      .map(i -> "count: " + i);
  }
}
