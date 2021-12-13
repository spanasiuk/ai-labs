package com.spanasiuk.lab3;

import com.spanasiuk.lab3.util.BacktrackingCSPUtil;

public class Lab3Application {

  //Startpoint
  public static void main(String[] args) {
    final String formula = "SEVEN + SEVEN + SIX == TWENTY";
    final String result = BacktrackingCSPUtil.solve(formula);
    if (result.isEmpty()) {
      System.out.println("No solution found");
    }
    System.out.println(result);
  }

}
