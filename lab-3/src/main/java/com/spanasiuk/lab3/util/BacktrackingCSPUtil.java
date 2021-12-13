package com.spanasiuk.lab3.util;

public final class BacktrackingCSPUtil {

  //solving method
  public static String solve(String q) {
    char c = 0;
    //go through all the chars
    for (int i = 0; i < q.length(); ++i) {
      //if letter, get it
      if (Character.isAlphabetic(q.charAt(i))) {
        c = q.charAt(i);
        break;
      }
    }
    if (c == 0) {
      //If no letters left, evaluate
      String[] ops = q.split("==");
      int o1 = eval(ops[0]), o2 = eval(ops[1]);
      if (o1 == o2) {
        return q;
      }
      return "";
    } else {
      //array for used digits
      char[] dset = new char[10];
      //marks all digits that are in use
      for (int i = 0; i < q.length(); ++i) {
        if (Character.isDigit(q.charAt(i))) {
          dset[q.charAt(i) - '0'] = 1;
        }
      }
      //go through all possible digits
      for (int i = 0; i < 10; ++i) {
        //if digit is unused, try replacing current char with it
        if (dset[i] == 0) {
          //recursively check if current digits are good or not
          String r = solve(q.replaceAll(String.valueOf(c),
              String.valueOf(i)));
          //if current digit mapping is correct
          if (!r.isEmpty()) {
            return r;
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
      if (next.equals("+")) {
        val += Integer.parseInt(st.nextToken().trim());
      } else if (next.equals("-")) {
        val -= Integer.parseInt(st.nextToken().trim());
      } else if (next.equals("*")) {
        val *= Integer.parseInt(st.nextToken().trim());
      } else if (next.equals("/")) {
        val /= Integer.parseInt(st.nextToken().trim());
      } else {
        val = Integer.parseInt(next);
      }
    }
    return val;
  }

}
