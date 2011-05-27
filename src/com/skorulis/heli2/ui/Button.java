package com.skorulis.heli2.ui;

import static forplay.core.ForPlay.*;

import forplay.core.CanvasLayer;
import forplay.core.Image;
import forplay.core.Pointer;

public class Button implements Pointer.Listener{

	private boolean pressed;
  private CanvasLayer layer;
	private int textColor;
	private String text;
	private Image bgImage;
	private ClickHandler clickHandler;
	private float transX,transY;
	private boolean visible;
	
	
	public Button(int width,int height) {
		layer = graphics().createCanvasLayer(width, height);
		textColor = 0xFF000000;
		visible = true;
	}
	
	public void setTranslation(int x,int y) {
	  this.transX = x; this.transY = y;
	  layer.setTranslation(x, y);
	}
	
	public CanvasLayer layer() {
		return layer;
	}
	
	public void setText(String text) {
	  this.text = text;
	  this.redraw();
	}
	
	public void setTextColor(int color) {
	  this.textColor = color;
	}
	
	public void setBgImage(Image image) {
	  this.bgImage = image;
	  redraw();
	}
	
	public void setClickHandler(ClickHandler click) {
	  this.clickHandler = click;
	}
	
	private void redraw() {
	  layer.canvas().clear();
	  if(bgImage!=null) {
	    layer.canvas().drawImage(bgImage, 0, 0, layer.canvas().width(), layer.canvas().height());
	  }
	  
	  layer.canvas().setFillColor(0xFFFF0000);
    layer.canvas().fillRect(0, 0, layer.canvas().width(), layer.canvas().height());
	  layer.canvas().setFillColor(textColor);
	  layer.canvas().drawText(text, 0, 20);
	  
	}

  @Override
  public void onPointerStart(float x, float y) {
      pressed = pointInButton(x, y);
  }

  @Override
  public void onPointerEnd(float x, float y) {
    pressed = false;
    if(pointInButton(x, y) && clickHandler!=null) {
      ClickEvent click = new ClickEvent();
      click.sender = this;
      clickHandler.onClick(click);
    }
  }

  public void setVisible(boolean visible) {
    layer.setScale(visible?1:0.0001f);
    this.visible = visible;
  }
  
  public boolean visible() {
    return visible;
  }
  
  @Override
  public void onPointerDrag(float x, float y) {
    // TODO Auto-generated method stub
    
  }
  
  private boolean pointInButton(float x,float y) {
    if(!visible()) {
      return false;
    }
    return (x >= transX && y >= transY && x <=transX+layer.canvas().width() && y <= transY+layer.canvas().width());
  }
	
	
	
}
