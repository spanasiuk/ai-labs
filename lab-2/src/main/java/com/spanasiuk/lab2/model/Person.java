package com.spanasiuk.lab2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {

  private Integer id;
  private Boolean isKnight;

  public String toString() {
    return String.format("[%s, %s]", id, isKnight);
  }

}
