package com.spanasiuk.lab3.util;

public final class BacktrackingCSPUtil {

  //solving method
  public static String solve(String formula) {
    char currentChar = 0;
    //go through all the chars
    for (int i = 0; i < formula.length(); ++i) {
      //if letter, get it
      if (Character.isAlphabetic(formula.charAt(i))) {
        currentChar = formula.charAt(i);
        break;
      }
    }
    if (currentChar == 0) {
      //If no letters left, evaluate
      String[] eqSides = formula.split("==");
      if (eval(eqSides[0]) == eval(eqSides[1])) {
        return formula;
      }
      return "";
    } else {
      //array for used digits. 1 means it's used
      char[] usedDigits = new char[10];
      //marks all digits that are in use
      for (int i = 0; i < formula.length(); ++i) {
        if (Character.isDigit(formula.charAt(i))) {
          usedDigits[formula.charAt(i) - '0'] = 1;
        }
      }
      //go through all possible digits
      for (int i = 0; i < 10; ++i) {
        //if digit is unused, try replacing current char with it
        if (usedDigits[i] == 0) {
          //recursively check if current digits are good or not
          String result = solve(formula.replaceAll(String.valueOf(currentChar),
              String.valueOf(i)));
          //if current digit mapping is correct
          if (!result.isEmpty()) {
            //digits shouldn't start with 0
            String[] numbers = result.split(" ");
            boolean hasZeroAtBeginning = false;
            for (String n : numbers) {
              if (n.charAt(0) == '0') {
                hasZeroAtBeginning = true;
                break;
              }
            }
            if (!hasZeroAtBeginning) {
              return result;
            }
          }
        }
      }
    }
    //current char mapping isn't correct
    return "";
  }

  //Split the formula and evaluate it
  private static int eval(String q) {
    int val = 0;
    java.util.StringTokenizer st = new java.util.StringTokenizer(q, "*/+-", true);
    while (st.hasMoreTokens()) {
      String next = st.nextToken().trim();
      switch (next) {
        case "+" -> val += Integer.parseInt(st.nextToken().trim());
        case "-" -> val -= Integer.parseInt(st.nextToken().trim());
        case "*" -> val *= Integer.parseInt(st.nextToken().trim());
        case "/" -> val /= Integer.parseInt(st.nextToken().trim());
        default -> val = Integer.parseInt(next);
      }
    }
    return val;
  }

}
