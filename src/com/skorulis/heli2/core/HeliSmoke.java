package com.skorulis.heli2.core;

import static forplay.core.ForPlay.*;
import com.skorulis.heli2.math.Vec2f;
import com.skorulis.heli2.base.Entity;
import com.skorulis.heli2.base.RenderComponent;
import com.skorulis.heli2.base.UpdateComponent;

import forplay.core.GroupLayer;
import forplay.core.ImageLayer;

public class HeliSmoke implements Entity,RenderComponent,UpdateComponent{

	private static final float fadeRate = 0.4f;
	private float life;
	private com.skorulis.heli2.math.Vec2f loc;
	public float landRate;
	private ImageLayer layer;
	
	public HeliSmoke(Vec2f loc,GroupLayer group){
		layer = graphics().createImageLayer(assetManager().getImage("images/smoke.png"));
		group.add(layer);
		this.loc = new Vec2f(loc.x,loc.y);
		life = 0.7f;
		layer.setScale(life);
	}
	
	public void update(float delta) {
		loc.x-=landRate*delta;
		life-=delta*fadeRate;
	}
	
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void render(float alpha) {
		layer.setTranslation(loc.x,loc.y);
		if(life >0) {
			layer.setScale(life);
		}
	}
	
	public void destroy() {
		layer.destroy();
	}
	
}
