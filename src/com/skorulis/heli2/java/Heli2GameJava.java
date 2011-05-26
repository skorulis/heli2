package com.skorulis.heli2.java;

import forplay.core.ForPlay;
import forplay.java.JavaAssetManager;
import forplay.java.JavaPlatform;
import com.skorulis.heli2.core.Heli2Game;;

public class Heli2GameJava {

  public static void main(String[] args) {
    JavaAssetManager assets = JavaPlatform.register().assetManager();
    assets.setPathPrefix("src/forplay/sample/hello/resources");
    ForPlay.run(new Heli2Game());
  }
  
}
