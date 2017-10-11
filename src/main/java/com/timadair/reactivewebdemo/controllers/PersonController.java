package com.timadair.reactivewebdemo.controllers;

import com.timadair.reactivewebdemo.repositories.ReactivePersonRepository;
import com.timadair.reactivewebdemo.resources.Person;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by timadair on 9/22/2017.
 */
@RestController
public class PersonController {

  private final ReactivePersonRepository personRepository;

  public PersonController(ReactivePersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @GetMapping(value = "/persons/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  Flux<Person> getPersons(@RequestParam(required = false) String surname) {
    if (StringUtils.isEmpty(surname)) {
      return personRepository.findAll().map(p -> println(p, "read"));
    }
    return personRepository.findBySurname(Mono.just(surname));
  }

  private Person println(Person d, String mode) {
    System.out.println(mode + ": " + d.getFullName());
    return d;
  }
}
