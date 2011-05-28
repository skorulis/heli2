//Class to paint the landscape using a group

package com.skorulis.heli2.core;
import static forplay.core.ForPlay.*;

import com.skorulis.heli2.base.RenderComponent;

import forplay.core.Canvas;
import forplay.core.CanvasLayer;
import forplay.core.GroupLayer;

public class LandscapePaintGroup implements RenderComponent{

	private Landscape landscape;
	private GroupLayer group;
	private CanvasLayer[] stripes;
	
	public LandscapePaintGroup(Landscape landscape,GroupLayer layer) {
		this.landscape = landscape;
		stripes = new CanvasLayer[landscape.topHeight.length];
		group = graphics().createGroupLayer();
		for(int i=0; i < stripes.length; ++i) {
			stripes[i] = graphics().createCanvasLayer(Landscape.SEGMENT_WIDTH, landscape.height);
			stripes[i].setTranslation(i*Landscape.SEGMENT_WIDTH, 0);
			group.add(stripes[i]);
		}
		
		layer.add(group);
	}
	
	public void redraw() {
		for(int i=0; i < stripes.length; ++i) {
			renderStripe(i, stripes[i]);
		}
	}
	
	public void renderStripe(int pos,CanvasLayer layer) {
		Canvas canvas = layer.canvas();
		canvas.clear();
		canvas.setFillColor(0xff00CC51);

		canvas.fillRect(0, 0, Landscape.SEGMENT_WIDTH+2, landscape.topHeight[pos]);
		canvas.fillRect(0, landscape.height-landscape.bottomHeight[pos], Landscape.SEGMENT_WIDTH+2, landscape.bottomHeight[pos]);
		if(landscape.centerHeight[pos] > 0) {
			int gap = (landscape.height-landscape.bottomHeight[pos]-landscape.topHeight[pos]-landscape.centerHeight[pos])/2;
			canvas.fillRect(0, landscape.topHeight[pos]+gap, Landscape.SEGMENT_WIDTH+2, landscape.centerHeight[pos]);
		}
		
	}

	@Override
	public void render(float alpha) {
		int pos;
		for(Integer i : landscape.toRedraw) {
			pos = i.intValue();
			renderStripe(pos, stripes[pos]);
		}
		for(int i=0; i < stripes.length; ++i) {
			pos = (i+landscape.slideI)%landscape.topHeight.length;
			int trans = (i-landscape.slideI)%landscape.topHeight.length;
			if(trans< 0) {
				trans+=stripes.length;
			}
			stripes[i].setTranslation(trans*Landscape.SEGMENT_WIDTH -landscape.slideF-1, 0);
		}
		landscape.toRedraw.clear();
	}
	
	public void update(float delta) {
		
	}
	
}
