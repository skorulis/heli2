package com.skorulis.heli2.core;

import static forplay.core.ForPlay.*;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;


import forplay.core.Game;
import forplay.core.Keyboard;
import forplay.core.Pointer;

public class Heli2Game implements Game,Pointer.Listener,Keyboard.Listener{

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
  private boolean paused;
  
  @Override
  public void init() {
	graphics().setSize(canvasWidth, canvasHeight);
    
    frameRate = new FrameRateCalc();
    smoke = new LinkedList<HeliSmoke>();
    
    landscape = new Landscape(canvasWidth,canvasHeight,graphics().rootLayer());
    helicopter = new Helicopter(canvasWidth,canvasHeight,graphics().rootLayer());
    
    pointer().setListener(this);
    keyboard().setListener(this);
    String best = storage().getItem("score");
    
    if(best!=null) {
    	bestScore = Float.parseFloat(best);
    }
     
    reset();
  }
  
  public void reset() {
	smoke.clear();
	helicopter.reset();		
	landscape.reset();
	for(int i=0; i < MAX_KEYS; ++i) {
		keys[i] = false;
	}
	mouseDown = false;
  }

  @Override
  public void update(float delta) {
	delta = delta/1000.0f;
	
    if(delta > 0.5f || paused) {
    	return; //Don't update when the delta values are very high
    }
    helicopter.update(delta);
    landscape.update(delta);
    Iterator<HeliSmoke> smokeIt = smoke.iterator();
    HeliSmoke hs;
    while(smokeIt.hasNext()) {
    	hs = smokeIt.next();
    	if(hs.isAlive()) {
    		hs.landRate = landscape.slideRate;
	    	hs.update(delta);
    	} else {
    		hs.destroy();
    		smokeIt.remove();
    		
    	}
    }
    if(smokeUpdate > 0.35f) {
		smokeUpdate = 0;
		smoke.add(new HeliSmoke(helicopter.loc,graphics().rootLayer()));
	} else {
		smokeUpdate+=delta;
	}
    score+=delta*landscape.slideRate;
    frameRate.addFrame(delta);
    checkKeys(delta);
    
  }
  
  private void checkKeys(float delta) {
	  if(keys['W'] || mouseDown) {
			helicopter.vel.y+= -130*delta;
		}
		if(keys['D']) {
			helicopter.vel.x+= 80*delta;
		}
		if(keys['A']) {
			helicopter.vel.x+= -80*delta;
		}
		if(keys['S']) {
			//helicopter.vel.y+= 80*delta;
		}
		if(landscape.collides(helicopter.box)) {
			onDeath();
		}
  }
  
  private void onDeath() {
	paused = true;
	bestScore = Math.max(score, bestScore);
	storage().setItem("score", ""+bestScore);
	landscape.setBestScore(bestScore);
	
  }

  @Override
  public void paint(float alpha) {
	  for(HeliSmoke hs: smoke) {
		  hs.render(alpha);
	  }
	  
	landscape.render(alpha);
    helicopter.render(alpha);
    landscape.canvas.canvas().setFillColor(0xFF000000);
    landscape.canvas.canvas().drawText("Score: " + (int)score, 10, 12);
	landscape.canvas.canvas().drawText("Best: " + (int) Math.max(score, bestScore), 10, 24);
	landscape.canvas.canvas().drawText("" + frameRate.getTmpFrameRate() + " frames a second",10,36);
  }

  @Override
  public int updateRate() {
    return 25;
  }

@Override
public void onPointerStart(float x, float y) {
	mouseDown = true;
}

@Override
public void onPointerEnd(float x, float y) {
	mouseDown = false;
}

@Override
public void onPointerDrag(float x, float y) {
	
}

@Override
public void onKeyDown(int keyCode) {
	keys[keyCode] = true;
}

@Override
public void onKeyUp(int keyCode) {
	keys[keyCode] = false;
}

}
