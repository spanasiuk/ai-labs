package com.spanasiuk.lab2.model.impl;

import com.spanasiuk.lab2.model.Beach;
import com.spanasiuk.lab2.model.Node;
import java.util.Arrays;

public class PersonRiverArray extends Node<Beach[]> {

  public PersonRiverArray(Node<Beach[]> parent, Beach[] value, int pathScore) {
    super(parent, value, pathScore);
  }

  public PersonRiverArray(Beach[] value) {
    super(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonRiverArray e = (PersonRiverArray) o;
    return Arrays.deepEquals(getValue(), e.getValue());
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(getValue());
  }

  @Override
  public String toString() {
    return String.format("Local score: %s; Value: %s", getLocalScore(), Arrays.toString(getValue()));
  }
}
