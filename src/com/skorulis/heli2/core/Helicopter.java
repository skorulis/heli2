package com.skorulis.heli2.core;

import static forplay.core.ForPlay.assetManager;
import static forplay.core.ForPlay.log;

import com.skorulis.heli2.base.RectBoundBox;
import com.skorulis.heli2.components.ClipLoc2f;
import com.skorulis.heli2.components.Gen2fComponent;
import com.skorulis.heli2.math.Vec2f;

import forplay.core.GroupLayer;
import forplay.core.Image;
import forplay.core.ImageLayer;
import forplay.core.ResourceCallback;

import static forplay.core.ForPlay.*;

public class Helicopter {

	ClipLoc2f loc;
	ClipLoc2f vel;
	Gen2fComponent acc;
	ImageLayer layer;
	Image image;
	Image image2;
	
	Vec2f target;
	boolean firing;
	float frameSwitch;
	
	RectBoundBox box;
	
	public Helicopter(int width,int height,final GroupLayer parentLayer) {	
		loc = new ClipLoc2f();
		loc.maxX = width-50; loc.maxY = height-30;
		vel = new ClipLoc2f(loc);
		vel.setBounds(-80, -80, 80, 80);
		acc = new Gen2fComponent(vel,0,30);
		image = assetManager().getImage("images/helicopter.png");
		image2 = assetManager().getImage("images/helicopter.png");
		layer = graphics().createImageLayer(image);
		box = new RectBoundBox();
    image.addCallback(new ResourceCallback<Image>() {
      @Override
      public void done(Image image) {      
        box.width = image.width();
        box.height = image.height();
        parentLayer.add(layer);
      }

      @Override
      public void error(Throwable err) {
        log().error("Error loading image!", err);
      }
    });
		
	}
	
	public void reset() {
		vel.x =0; vel.y = 0;
		loc.y = loc.maxY/2;
		loc.x = 0;
	}

	public void render(float alpha) {
	  if(frameSwitch > 0.2f) {
		  if(layer.image()==image) {
			  layer.setImage(image2);
		  } else {
			  layer.setImage(image);
		  }
		 frameSwitch=0;
	  }
	}

	public void update(float delta) {
		acc.update(delta);
		vel.update(delta);
		layer.setTranslation(loc.x, loc.y);
		box.width = image.width()-8;
		box.height = image.height()-8;
		box.x = loc.x+16;
		box.y = loc.y+16;
		frameSwitch+=delta;
	}
	
}
