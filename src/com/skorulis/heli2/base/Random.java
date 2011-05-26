package com.skorulis.heli2.base;

public class Random {

  private static java.util.Random rand = new java.util.Random(); 
  
  public static int nextInt(int max) {
    return rand.nextInt(max);
  }
  
}
