package com.spanasiuk.lab2.util.util;

import com.spanasiuk.lab2.model.Beach;
import com.spanasiuk.lab2.model.Node;
import com.spanasiuk.lab2.model.Person;
import com.spanasiuk.lab2.model.impl.PersonRiverArray;
import com.spanasiuk.lab2.util.BeamSearchUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PersonRiverBeamSearchUtil extends BeamSearchUtil<Beach[]> {

  public PersonRiverBeamSearchUtil(int depth) {
    super(depth);
  }

  @Override
  public List<Node<Beach[]>> getNextNodes(Node<Beach[]> currentNode, Node<Beach[]> endNode) {
    final List<Node<Beach[]>> result = new ArrayList<>();

    final Beach[] nodeValue = currentNode.getValue();
    if (nodeValue[0].getBoatPresent()) {
      //generate all possible moves from the startBeach to the endBeach
      generateAllPossibleMoves(currentNode, result, true);
    } else {
      //generate all possible moves from the endBeach to the startBeach
      generateAllPossibleMoves(currentNode, result, false);
    }

    //return only DEPTH amount of best nodes
    result.forEach(node -> node.setHeuristicScore(getHeuristic(node, endNode)));
    return result;
  }

  private void generateAllPossibleMoves(Node<Beach[]> currentNode, List<Node<Beach[]>> result, boolean moveForward) {
    final Beach[] nodeValue = currentNode.getValue();
    final int peopleAmount = nodeValue[moveForward ? 0 : 1].getPersonList().size();
    for (int i = 0; i < peopleAmount; i++) {
      result.add(new PersonRiverArray(currentNode, moveToAnotherBeach(currentNode, moveForward, i), getPathScore()));
      for (int j = i + 1; j < peopleAmount; j++) {
        result.add(
            new PersonRiverArray(currentNode, moveToAnotherBeach(currentNode, moveForward, i, j), getPathScore()));
      }
    }
  }

  @Override
  protected int getHeuristic(Node<Beach[]> currentNode, Node<Beach[]> endNode) {
    final Beach[] currentState = currentNode.getValue();
    //if restriction isn't met, make heuristic very bad
    if (!restrictionsMet(currentState)) {
      return Integer.MAX_VALUE;
    }
    int result = 0;
    final List<Person> startBeach = currentState[0].getPersonList();
    final List<Person> endBeach = currentState[1].getPersonList();

    //if no one left at the start beach, the goal is reached
    if (startBeach.stream()
        .allMatch(Objects::isNull)) {
      return Integer.MIN_VALUE;
    }
    //for each squire at the end beach we add 2 points and 1 point for each knight
    for (Person person : endBeach) {
      if (!person.getIsKnight()) {
        result -= 2;
      } else {
        result -= 1;
      }
    }
    //for each squire at the start beach we remove 1 point and 2 point for each knight
    for (Person person : startBeach) {
      if (!person.getIsKnight()) {
        result += 1;
      } else {
        result += 2;
      }
    }
    return result;
  }

  //check if there's no squire left with not their knights
  private boolean restrictionsMet(Beach[] currentState) {
    for (Beach beach : currentState) {
      Set<Integer> mainPersonSet = new HashSet<>();
      //get all knights at the beach
      for (Person person : beach.getPersonList()) {
        if (person.getIsKnight()) {
          mainPersonSet.add(person.getId());
        }
      }
      //check if each squire has his knight on the same beach, or there's no knights left.
      //Otherwise restriction isn't met
      for (Person person : beach.getPersonList()) {
        if (!person.getIsKnight() && !mainPersonSet.contains(person.getId()) && !mainPersonSet.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  private Beach[] moveToAnotherBeach(Node<Beach[]> node, boolean moveForward, int... indexesToMove) {
    final Beach[] currentState = node.getValue();
    final List<Person> startBeachPeople = new ArrayList<>(currentState[0].getPersonList());
    final List<Person> endBeachPeople = new ArrayList<>(currentState[1].getPersonList());

    if (moveForward) {
      Arrays.stream(indexesToMove)
          .mapToObj(startBeachPeople::get)
          .toList()
          .forEach(p -> {
            if (startBeachPeople.remove(p)) {
              endBeachPeople.add(p);
            }
          });
    } else {
      Arrays.stream(indexesToMove)
          .mapToObj(endBeachPeople::get)
          .toList()
          .forEach(p -> {
            if (endBeachPeople.remove(p)) {
              startBeachPeople.add(p);
            }
          });
    }

    return new Beach[]{new Beach(startBeachPeople, !moveForward), new Beach(endBeachPeople, moveForward)};
  }

  @Override
  protected int getPathScore() {
    return 1;
  }

}
