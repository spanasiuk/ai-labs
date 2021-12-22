package com.spanasiuk.lab2;

import com.spanasiuk.lab2.model.Beach;
import com.spanasiuk.lab2.model.Node;
import com.spanasiuk.lab2.model.Person;
import com.spanasiuk.lab2.model.impl.PersonRiverArray;
import com.spanasiuk.lab2.util.util.PersonRiverBeamSearchUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab2Application {

  //Startpoint
  public static void main(String[] args) {
    final PersonRiverBeamSearchUtil beamSearchUtil = new PersonRiverBeamSearchUtil(10);
    final Node<Beach[]> startNode = new PersonRiverArray(new Beach[]{
        new Beach(new ArrayList<>(List.of(
            new Person(1, true), new Person(1, false),
            new Person(2, true), new Person(2, false),
            new Person(3, true), new Person(3, false))), true),
        new Beach(new ArrayList<>(), false)});
    final List<Node<Beach[]>> shortestPath = beamSearchUtil.findShortestPath(startNode, null);
    shortestPath.forEach(node -> System.out.println(Arrays.toString(node.getValue())));
    System.out.println("Path length is " + shortestPath.size());
  }

}
