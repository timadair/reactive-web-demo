package com.timadair.reactivewebdemo.controllers;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

  @GetMapping(value = "/flux/endless", produces = "text/event-stream")
  public Flux<String> getEndlessFlux() {
    return Flux.interval(Duration.ofMillis(50))
      .map(this::mapAndPrint);
  }

  private String mapAndPrint(Long i) {
    System.out.println(i);
    return "count: " + i;
  }

  @GetMapping(value = "/mono")
  public Mono<List<String>> getMono() {
    return Flux.interval(Duration.ofMillis(50))
      .take(100)
      .map(i -> "Count: " + i)
      .collectList();
  }

  @GetMapping(value = "/flowable", produces = "text/event-stream")
  public Flowable<String> getFlowable() {
    return Flowable.interval(50, TimeUnit.MILLISECONDS)
      .take(100)
      .map(i -> "Count: " + i);
  }

  @GetMapping(value = "/observable", produces = "text/event-stream")
  public Observable<String> getObservable() {
    return Observable.interval(50, TimeUnit.MILLISECONDS)
      .take(100)
      .map(i -> "Count: " + i);
  }

  @GetMapping(value = "/single")
  public Single<String> getSingle() {
    return Flowable.interval(50, TimeUnit.MILLISECONDS)
      .take(100)
      .map(i -> "Count: " + i)
      .elementAt(100, "Exceeded production target");
  }

  @GetMapping(value = "/maybe")
  public Maybe<String> getMaybe() {
    return Observable.interval(50, TimeUnit.MILLISECONDS)
      .take(100)
      .map(i -> "Count: " + i)
      .elementAt(99);
  }

  @GetMapping(value = "/maybe-not")
  public Maybe<String> getMissingMaybe() {
    return Observable.interval(50, TimeUnit.MILLISECONDS)
      .take(100)
      .map(i -> "Count: " + i)
      .elementAt(100);
   }
}
