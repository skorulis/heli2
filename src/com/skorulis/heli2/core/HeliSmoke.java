package com.skorulis.heli2.core;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.skorulis.heli2.math.Vec2f;
import com.skorulis.heli2.base.Entity;
import com.skorulis.heli2.base.RenderComponent;
import com.skorulis.heli2.base.UpdateComponent;

public class HeliSmoke implements Entity,RenderComponent,UpdateComponent{

	private static final float fadeRate = 0.4f;
	private float life;
	private com.skorulis.heli2.math.Vec2f loc;
	public float landRate;
	CssColor color = CssColor.make("rgba(55,55,55,1)");
	
	public HeliSmoke(Vec2f loc){ 
		this.loc = new Vec2f(loc.x,loc.y);
		life = 0.7f;
	}
	
	public void update(float delta) {
		loc.x-=landRate*delta;
		life-=delta*fadeRate;
	}
	
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void render(Context2d context) {
		context.setGlobalAlpha(Math.max(life,0));
		context.setFillStyle(color);
		context.fillRect(loc.x, loc.y, 20, 20);
		context.fill();
		context.setGlobalAlpha(1);
	}
	
}
