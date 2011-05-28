package com.skorulis.heli2.ui;

import static forplay.core.ForPlay.*;

import forplay.core.CanvasLayer;
import forplay.core.Image;
import forplay.core.Pointer;
import forplay.core.ResourceCallback;

public class Button implements Pointer.Listener,ResourceCallback<Image>{

	private boolean pressed;
	private CanvasLayer layer;
	private int textColor;
	private String text;
	private Image foreImage;
	private Image bgImage;
	private ClickHandler clickHandler;
	private float transX,transY;
	private boolean visible;
	
	public int textX,textY;
	
	
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
	
private void redrawNowOrLater(Image image) {
	if(image==null || image.isReady()) {
		redraw();
	} else {
		image.addCallback(this);
	}
}
	
	public void setForeImage(Image image) {
		this.foreImage=image;
		redrawNowOrLater(foreImage);
	}
	
	public void setForeImage(String path) {
		this.foreImage = assetManager().getImage(path);
		redrawNowOrLater(foreImage);
	}
	
	public void setBgImage(Image image) {
	  this.bgImage = image;
	  redrawNowOrLater(bgImage);
	}
	
	public void setBgImage(String path) {
		this.bgImage = assetManager().getImage(path);
		redrawNowOrLater(bgImage);
	}
	
	public void setClickHandler(ClickHandler click) {
	  this.clickHandler = click;
	}
	
	private void redraw() {
	  layer.canvas().clear();
	  if(bgImage!=null) {
	    layer.canvas().drawImage(bgImage, 0, 0, layer.canvas().width(), layer.canvas().height());
	  }
	  
	  if(text!=null) {
		  layer.canvas().setFillColor(textColor);
		  layer.canvas().drawText(text, textX, textY);
	  }
	  if(foreImage!=null) {
		  layer.canvas().drawImage(foreImage, 0, 0, layer.canvas().width(), layer.canvas().height());
	  }
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
	  this.visible = visible;
	  if(visible) {
		redraw();
	} else {
		layer.canvas().clear();
	}
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

@Override
public void done(Image resource) {
	redraw();
}

@Override
public void error(Throwable err) {
	// TODO Auto-generated method stub
	
}
	
	
	
}
