package com.timadair.reactivewebdemo.repositories;

import com.timadair.reactivewebdemo.resources.Person;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by timadair on 9/22/2017.
 */
public interface ReactivePersonRepository extends ReactiveCrudRepository<Person, String> {

  Flux<Person> findBySurname(Mono<String> surname);

  @Query("{ 'givenName': ?0, 'surname': ?1}")
  Mono<Person> findByGivenAndSurname(String givenName, String surname);
}