package com.spanasiuk.lab2.util;

import com.spanasiuk.lab2.model.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public abstract class BeamSearchUtil<T> implements LocalSearchUtil<T> {

  private PriorityQueue<Node<T>> activeNodes = new PriorityQueue<>(Node::compareTo);
  private HashMap<Node<T>, Node<T>> closedNodes = new HashMap<>();
  protected final int depth;

  public BeamSearchUtil(int depth) {
    this.depth = depth;
  }

  @Override
  public List<Node<T>> findShortestPath(Node<T> startNode, Node<T> endNode) {
    if (startNode.getHeuristicScore() == null) {
      startNode.setHeuristicScore(getHeuristic(startNode, endNode));
    }
    if (startNode.getHeuristicScore() == Integer.MIN_VALUE) {
      return collectResult(startNode);
    }

    activeNodes.add(startNode);
    while (!activeNodes.isEmpty()) {
      //get node that's closest to the end result and mark it as closed
      final Node<T> minNode = activeNodes.poll();
      closedNodes.put(minNode, minNode);
      //if current node is at the end, return result
      if (minNode.getHeuristicScore() == Integer.MIN_VALUE) {
        return collectResult(minNode);
      }
      //generate all possible next nodes and remove those that were already generated
      final List<Node<T>> nextNodes = getNextNodes(minNode, endNode).stream()
          .sorted()
          //filter out all nodes but the DEPTH amount
          .limit(depth)
          .filter(node -> {
            if (closedNodes.containsKey(node)) {
              final Node<T> closedNode = closedNodes.get(node);
              //if current node is less expensive than equivalent closed node, update closed node parent
              if (node.getGlobalScore() < closedNode.getGlobalScore()) {
                closedNode.setParent(node.getParent(), getPathScore());
              }
              return false;
            }
            return !activeNodes.contains(node);
          })
          .toList();
      activeNodes.addAll(nextNodes);
    }
    System.err.println("There's no solution");
    return new ArrayList<>();
  }

  protected abstract int getHeuristic(Node<T> currentNode, Node<T> endNode);

  protected abstract int getPathScore();

  //constructs the result going backwards from the end
  private List<Node<T>> collectResult(Node<T> node) {
    final LinkedList<Node<T>> result = new LinkedList<>();
    result.addFirst(node);
    Node<T> currentNode = node;
    while (currentNode.getParent() != null) {
      final Node<T> parent = currentNode.getParent();
      result.addFirst(parent);
      currentNode = parent;
    }
    return result;
  }

}
