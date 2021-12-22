package com.spanasiuk.lab2.util;

import com.spanasiuk.lab2.model.Node;
import java.util.List;

public interface LocalSearchUtil<T> {

  List<Node<T>> findShortestPath(Node<T> startNode, Node<T> endNode);

  List<Node<T>> getNextNodes(Node<T> currentNode, Node<T> endNode);

}
