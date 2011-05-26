package com.skorulis.heli2.ui;

import static forplay.core.ForPlay.*;
import forplay.core.CanvasLayer;

public class Button {

	private CanvasLayer layer;
	
	public Button(int width,int height) {
		layer = graphics().createCanvasLayer(width, height);
	}
	
	public CanvasLayer layer() {
		return layer();
	}
	
}
