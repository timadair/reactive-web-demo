package com.timadair.reactivewebdemo.resources;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by timadair on 9/22/2017.
 */
@Document(collection = "persons")
public class Person {
  @Id
  private String id;
  private String surname;
  private String givenName;

  public Person() {
  }

  public Person(String givenName, String surname) {
    this.givenName = givenName;
    this.surname = surname;
  }

  public String getFullName() {
    return surname + ", " + givenName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }
}
