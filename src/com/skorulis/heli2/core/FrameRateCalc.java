package com.skorulis.heli2.core;

public class FrameRateCalc {

  private double last;
  
	private int frames;
	private double time;
	private int tmpFrames;
	private double tmpTime;
	
	private double tmpReset;
	private double tmpFrameRate;
	
	public FrameRateCalc() {
		frames=0; time = 0;
		tmpReset = 3;
		tmpFrames = 0; tmpTime = 0;
	}
	
	public void frame(double current) {
	  double delta = (current-last)/1000.0;
	  last = current;
	  if(delta > 1.0) {
	    return;
	  }
		frames++; tmpFrames++;
		this.time+=delta;
		tmpTime+=delta;
		if(tmpTime>tmpReset) {
			tmpFrameRate = tmpFrames/tmpTime;
			tmpTime = 0; tmpFrames = 0;
		}
	}
	
	public double getFrameRate() {
		return frames/time;
	}
	
	public double getTmpFramerate() {
		return tmpFrameRate;
	}
	
	public String getTmpFramerateText() {
		return ""+(int) tmpFrameRate;
	}
	
}
