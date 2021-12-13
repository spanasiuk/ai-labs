package com.spanasiuk.lab1;

import com.spanasiuk.lab1.model.Node;
import com.spanasiuk.lab1.model.impl.IntArrayNode;
import com.spanasiuk.lab1.util.impl.AStarIntArrayUtil;
import java.util.Arrays;
import java.util.List;

public class Lab1Application {

  //Startpoint
  public static void main(String[] args) {
    final AStarIntArrayUtil aStarIntArrayUtil = new AStarIntArrayUtil();
    final Node<int[]> startNode = new IntArrayNode(new int[]{1, 2, 3, 4, 0, -1, -2, -3});
    final Node<int[]> endNode = new IntArrayNode(new int[]{-1, -2, -3, 0, 1, 2, 3, 4});
    final List<Node<int[]>> shortestPath = aStarIntArrayUtil.findShortestPath(startNode, endNode);
    shortestPath.forEach(node -> System.out.println(Arrays.toString(node.getValue())));
    System.out.println("Path length is " + shortestPath.size());
  }

}
