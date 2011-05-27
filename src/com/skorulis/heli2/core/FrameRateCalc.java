package com.skorulis.heli2.core;

import static forplay.core.ForPlay.*;

public class FrameRateCalc {

  private double last;
  
	private int frames;
	private double time;
	private int tmpFrames;
	private double tmpTime;
	
	private int tmpReset;
	private double tmpFrameRate;
	
	public FrameRateCalc() {
		frames=0; time = 0;
		tmpReset = 100;
		tmpFrames = 0; tmpTime = 0;
	}
	
	public void frame() {
	  double current = currentTime();
	  double delta = (current-last)/1000.0;
	  last = current;
	  log().debug("D " +delta);
	  if(delta > 1.0) {
	    return;
	  }
		frames++; tmpFrames++;
		this.time+=delta;
		tmpTime+=delta;
		if(tmpFrames>tmpReset) {
			tmpFrameRate = tmpFrames/tmpTime;
			tmpTime = 0; tmpFrames = 0;
		}
	}
	
	public double getFrameRate() {
		return frames/time;
	}
	
	public double getTmpFrameRate() {
		return tmpFrameRate;
	}
	
}
