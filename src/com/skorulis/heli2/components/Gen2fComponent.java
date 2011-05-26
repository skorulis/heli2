package com.skorulis.heli2.components;

import com.skorulis.heli2.math.Vec2f;
import com.skorulis.heli2.base.UpdateComponent;

public class Gen2fComponent extends Vec2f implements UpdateComponent{

	private Gen2fComponent child;
	
	public Gen2fComponent() {
		
	}
	
	public Gen2fComponent(Gen2fComponent child) {
		this.child = child;
	}
	
	public Gen2fComponent(Gen2fComponent child,float x,float y) {
		this.child = child;
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public void update(float delta) {
		child.setX(child.x + x*delta);
		child.setY(child.y + y*delta);
	}

	
	
}
