package com.spanasiuk.lab1.model;

import java.util.Objects;
import lombok.Getter;

public class Node<T> implements Comparable<Node<T>> {

  @Getter
  private Node<T> parent;
  @Getter
  private Integer heuristicScore;
  private int pathScore;
  @Getter
  private T value;

  public Node(Node<T> parent, T value, int pathScore) {
    this(value);
    setParent(parent, pathScore);
  }

  public Node(T value) {
    this.value = value;
  }

  public void setParent(Node<T> parent, int pathScore) {
    this.parent = parent;
    this.pathScore = pathScore;
  }

  public int getGlobalScore() {
    return parent == null ? 0 : parent.getGlobalScore() + pathScore;
  }

  public void setHeuristicScore(int heuristicScore) {
    this.heuristicScore = heuristicScore;
  }

  public Integer getLocalScore() {
    return heuristicScore + getGlobalScore();
  }

  @Override
  public int compareTo(Node<T> o) {
    final int result = this.getHeuristicScore().compareTo(o.getHeuristicScore());
    return result == 0 ? this.getLocalScore().compareTo(o.getLocalScore()) : result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node e = (Node) o;
    return Objects.equals(value, e.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public String toString() {
    return String.format("Local score: %s; Value: %s", getLocalScore(), getValue());
  }

}
