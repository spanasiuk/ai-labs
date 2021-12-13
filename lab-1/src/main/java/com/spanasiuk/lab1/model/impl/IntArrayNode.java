package com.spanasiuk.lab1.model.impl;

import com.spanasiuk.lab1.model.Node;
import java.util.Arrays;

public class IntArrayNode extends Node<int[]> {

  public IntArrayNode(Node<int[]> parent, int[] value, int pathScore) {
    super(parent, value, pathScore);
  }

  public IntArrayNode(int[] value) {
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
    IntArrayNode e = (IntArrayNode) o;
    return Arrays.equals(getValue(), e.getValue());
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(getValue());
  }

  @Override
  public String toString() {
    return String.format("Local score: %s; Value: %s", getLocalScore(), Arrays.toString(getValue()));
  }
}
