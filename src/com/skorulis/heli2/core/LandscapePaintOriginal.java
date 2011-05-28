package com.skorulis.heli2.core;

import static forplay.core.ForPlay.graphics;

import com.skorulis.heli2.base.RenderComponent;

import forplay.core.CanvasLayer;
import forplay.core.GroupLayer;

public class LandscapePaintOriginal implements RenderComponent{

	private Landscape landscape;
	public CanvasLayer canvas;
	
	public LandscapePaintOriginal(Landscape landscape,GroupLayer layer) {
		this.landscape = landscape;
		canvas = graphics().createCanvasLayer(landscape.width, landscape.height);
		layer.add(canvas);
	}
	
	@Override
	public void render(float alpha) {
		canvas.canvas().clear();
		canvas.canvas().setFillColor(0xff11CC51);
		int pos;
		int bestPos = -1;
		int bestI=-1;
		for(int i=0; i < landscape.topHeight.length; ++i) {
			pos = (i+landscape.slideI)%landscape.topHeight.length;
			if(bestPos<0 && landscape.isPosBestScore(i+landscape.slideI)) {
				bestPos = pos;
				bestI = i;
			} else {
				canvas.canvas().fillRect(i*Landscape.SEGMENT_WIDTH - landscape.slideF-1, 0, Landscape.SEGMENT_WIDTH+2, landscape.topHeight[pos]);
				canvas.canvas().fillRect(i*Landscape.SEGMENT_WIDTH - landscape.slideF-1, landscape.height-landscape.bottomHeight[pos], Landscape.SEGMENT_WIDTH+2, landscape.bottomHeight[pos]);
				if(landscape.centerHeight[pos] > 0) {
					int gap = (landscape.height-landscape.bottomHeight[pos]-landscape.topHeight[pos]-landscape.centerHeight[pos])/2;
					canvas.canvas().fillRect(i*Landscape.SEGMENT_WIDTH - landscape.slideF-1, landscape.topHeight[pos]+gap, Landscape.SEGMENT_WIDTH+2, landscape.centerHeight[pos]);
				}
			}	
		}
		canvas.canvas().setFillColor(0xff1165CE);
		if(bestPos >=0) {
			canvas.canvas().fillRect(bestI*Landscape.SEGMENT_WIDTH - landscape.slideF-1, 0, Landscape.SEGMENT_WIDTH+2, landscape.topHeight[bestPos]);
			canvas.canvas().fillRect(bestI*Landscape.SEGMENT_WIDTH - landscape.slideF-1, landscape.height-landscape.bottomHeight[bestPos], Landscape.SEGMENT_WIDTH+2, landscape.bottomHeight[bestPos]);
			if(landscape.centerHeight[bestPos] > 0) {
				int gap = (landscape.height-landscape.bottomHeight[bestPos]-landscape.topHeight[bestPos]-landscape.centerHeight[bestPos])/2;
				canvas.canvas().fillRect(bestI*Landscape.SEGMENT_WIDTH - landscape.slideF-1, landscape.topHeight[bestPos]+gap, Landscape.SEGMENT_WIDTH+2, landscape.centerHeight[bestPos]);
			}
		}
	}
	
}
