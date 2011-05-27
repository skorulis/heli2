package com.skorulis.heli2.html;

import com.skorulis.heli2.core.Heli2Game;

import forplay.core.ForPlay;
import forplay.html.HtmlAssetManager;
import forplay.html.HtmlGame;
import forplay.html.HtmlPlatform;

public class Heli2GameHtml extends HtmlGame{

  @Override
  public void start() {
    HtmlAssetManager assets = HtmlPlatform.register().assetManager();
    assets.setPathPrefix("heli2/");
    ForPlay.run(new Heli2Game());
  }
  
}
