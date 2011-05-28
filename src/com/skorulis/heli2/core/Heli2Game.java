package com.skorulis.heli2.core;

import static forplay.core.ForPlay.*;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;

import com.skorulis.heli2.ui.Button;
import com.skorulis.heli2.ui.ClickEvent;
import com.skorulis.heli2.ui.ClickHandler;


import forplay.core.CanvasLayer;
import forplay.core.Game;
import forplay.core.Keyboard;
import forplay.core.Net.Callback;
import forplay.core.Pointer;
import forplay.java.json.JSONObject;

public class Heli2Game implements Game,Pointer.Listener,Keyboard.Listener,Callback{

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
  private Button button;
  private int scoreId;
  private int rank;
  
  private CanvasLayer textCanvas;
  
  @Override
  public void init() {
	graphics().setSize(canvasWidth, canvasHeight);
    
    frameRate = new FrameRateCalc();
    smoke = new LinkedList<HeliSmoke>();
    
    landscape = new Landscape(canvasWidth,canvasHeight,graphics().rootLayer());
    helicopter = new Helicopter(canvasWidth,canvasHeight,graphics().rootLayer());
    
    pointer().setListener(this);
    keyboard().setListener(this);
    String bestS = storage().getItem("score");
    String rankS = storage().getItem("rank");
    String idS = storage().getItem("id");
    if(bestS!=null) {
    	bestScore = Float.parseFloat(bestS);
    }
    if(rankS!=null) {
    	rank = Integer.parseInt(rankS);
    }
    if(idS!=null) {
    	scoreId = Integer.parseInt(idS);
    }
    
    landscape.setBestScore(bestScore);
    button = new Button(120, 50);
    graphics().rootLayer().add(button.layer());
    button.setBgImage("images/buttonBg.png");
    button.setForeImage("images/startText.png");
    button.setTranslation(220, 175);
    button.setClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent e) {
        reset();
        paused = false;
        button.setVisible(false);
      }
    });
    
    paused = true;
    helicopter.layer.setScale(0.01f);
    
    textCanvas = graphics().createCanvasLayer(200, 100);
    graphics().rootLayer().add(textCanvas);
  }
  
  public void reset() {
    for(HeliSmoke hs: smoke) {
      hs.destroy();
    }
    score = 0;
    helicopter.layer.setScale(1);
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
		if(landscape.collides(helicopter.box)) {
			onDeath();
		}
  }
  
  private void onDeath() {
    paused = true;
    
    if(score > bestScore) {
    	bestScore = score;
    }
    if(bestScore > Config.instance().minScoreSubmit()) {
      long ts = (long)currentTime();
    	String netUrl = Config.instance().scoreURL()+"?score="+(int)bestScore+"&id="+scoreId+"&timestamp="+ts;
    	net().get(netUrl,this);
	}

  	storage().setItem("score", ""+bestScore);
  	
  	landscape.setBestScore(bestScore);
  	button.setForeImage("images/restartText.png"); 
  	button.setVisible(true);

  }

  @Override
  public void paint(float alpha) {
    frameRate.frame(currentTime());
	  for(HeliSmoke hs: smoke) {
		  hs.render(alpha);
	  }
	  
	landscape.render(alpha);
    helicopter.render(alpha);
    textCanvas.canvas().clear();
    textCanvas.canvas().setFillColor(0xFF000000);
    textCanvas.canvas().drawText("Score: " + (int)score, 10, 12);
    
    
    String bestText = "Best: " + (int) Math.max(score, bestScore);
    if(rank > 0) {
    	bestText+= " (rank " + rank + ")";
    }
    textCanvas.canvas().drawText(bestText, 10, 24);
    
    textCanvas.canvas().drawText("" + frameRate.getTmpFrameRate() + " frames a second",10,36);
  }

  @Override
  public int updateRate() {
    return 25;
  }

@Override
public void onPointerStart(float x, float y) {
	mouseDown = true;
	button.onPointerStart(x, y);
}

@Override
public void onPointerEnd(float x, float y) {
	mouseDown = false;
	button.onPointerEnd(x, y);
}

@Override
public void onPointerDrag(float x, float y) {
	button.onPointerDrag(x, y);
}

@Override
public void onKeyDown(int keyCode) {
	keys[keyCode] = true;
}

@Override
public void onKeyUp(int keyCode) {
	keys[keyCode] = false;
}

@Override
public void success(String response) {
	log().debug("RES" +response);
	try {
		String[] tokens = response.split(" ");
		scoreId = Integer.parseInt(tokens[0].trim());
		rank = Integer.parseInt(tokens[1].trim());
		storage().setItem("rank", ""+rank);
		storage().setItem("id", ""+scoreId);
	} catch(Exception e) {
		e.printStackTrace();
	}
	
}

@Override
public void failure(Throwable error) {
log().debug("NET FAIL " + error);
}


}
