package com.spanasiuk.lab1.util.impl;

import com.spanasiuk.lab1.model.Node;
import com.spanasiuk.lab1.model.impl.IntArrayNode;
import com.spanasiuk.lab1.util.AStarUtil;
import java.util.ArrayList;
import java.util.List;

public class AStarIntArrayUtil extends AStarUtil<int[]> {

  @Override
  //logic of calculating distance to the end
  protected int getHeuristic(Node<int[]> currentNode, Node<int[]> endNode) {
    int result = 0;
    final int[] currentNodeValue = currentNode.getValue();
    final int[] endNodeValue = endNode.getValue();
    //gets index of each value and compares it to the expected end index of that value
    for (int i = 0; i < currentNodeValue.length; i++) {
      int k = currentNodeValue[i];
      if (k == 0){
        continue;
      }
      int j;
      for (j = 0; j < endNodeValue.length; j++) {
        if (k == endNodeValue[j]) {
          break;
        }
      }
      result += Math.abs(i - j);
    }
    return result;
  }

  @Override
  protected int getPathScore() {
    return 1;
  }

  @Override
  protected List<Node<int[]>> getNextNodes(Node<int[]> currentNode, Node<int[]> endNode) {
    final List<Node<int[]>> result = new ArrayList<>();
    final int[] value = currentNode.getValue();
    int emptyId = -1;
    //looking for the empty hole, which should have value 0
    final int arraySize = value.length;
    for (int i = 0; i < arraySize; i++) {
      if (value[i] == 0) {
        emptyId = i;
      }
    }
    //if no empty holes, there's no moves
    if (emptyId == -1) {
      return result;
    }
    //move ball to the empty hole on the right of it
    if (emptyId > 0) {
      result.add(new IntArrayNode(currentNode, swapItems(value, emptyId, emptyId - 1), getPathScore()));
    }
    //move ball to the empty hole that's behind another ball to the right of it
    if (emptyId > 1) {
      result.add(new IntArrayNode(currentNode, swapItems(value, emptyId, emptyId - 2), getPathScore()));
    }
    //move ball to the empty hole on the left of it
    if (emptyId < arraySize - 1) {
      result.add(new IntArrayNode(currentNode, swapItems(value, emptyId, emptyId + 1), getPathScore()));
    }
    //move ball to the empty hole that's behind another ball to the left of it
    if (emptyId < arraySize - 2) {
      result.add(new IntArrayNode(currentNode, swapItems(value, emptyId, emptyId + 2), getPathScore()));
    }
    //calculating distance to the end
    result.forEach(node -> node.setHeuristicScore(getHeuristic(node, endNode)));
    return result;
  }

  //method to move balls
  private int[] swapItems(int[] originalArray, int firstIndex, int secondIndex) {
    final int[] clone = originalArray.clone();
    final int temp = clone[firstIndex];
    clone[firstIndex] = clone[secondIndex];
    clone[secondIndex] = temp;
    return clone;
  }
}
