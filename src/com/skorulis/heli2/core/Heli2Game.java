package com.skorulis.heli2.core;

import static forplay.core.ForPlay.*;

import java.util.LinkedList;

import forplay.core.Game;

public class Heli2Game implements Game{

  private LinkedList<HeliSmoke> smoke;
  private int MAX_KEYS = 256;
  private float score,bestScore;
  private float smokeUpdate;
  boolean keys[] = new boolean[MAX_KEYS];
  private Helicopter helicopter;
  private Landscape landscape;
  private boolean mouseDown;
  private FrameRateCalc frameRate;
  private final static int canvasWidth = 600;
  private final static int canvasHeight = 400;
  
  @Override
  public void init() {
    assetManager().getImage("images/helicopter.png");
    
    frameRate = new FrameRateCalc();
    smoke = new LinkedList<HeliSmoke>();
    
    landscape = new Landscape(canvasWidth,canvasHeight);
    helicopter = new Helicopter(canvasWidth,canvasHeight,graphics().rootLayer());
    
    
    
  }

  @Override
  public void update(float delta) {
    helicopter.update(delta);
    landscape.update(delta);
  }

  @Override
  public void paint(float alpha) {
    
  }

  @Override
  public int updateRate() {
    // TODO Auto-generated method stub
    return 0;
  }

}
