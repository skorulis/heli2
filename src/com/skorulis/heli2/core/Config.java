package com.skorulis.heli2.core;

public class Config {
	
	private String scoreURL;
	private int minScoreSubmit;
	private String htmlResourceDir;
	
	private Config() {
		
	}
	
	public static Config instance() {
		//return devConfig();
	  return prodConfig();
	}
	
	private static Config devConfig() {
		Config ret = new Config();
		ret.scoreURL = "http://127.0.0.1:1487/score/Heli";
		ret.minScoreSubmit = 10;
		ret.htmlResourceDir = "/heli2/";
		return ret;
	}
	
	private static Config prodConfig() {
	  Config ret = new Config();
    ret.scoreURL = "http://skorulis.com/score/Heli";
    ret.minScoreSubmit = 500;
    ret.htmlResourceDir = "../../content/gwt/heli2/heli2/";
    return ret;
	}
	
	public String scoreURL() {
		return scoreURL;
	}
	
	public int minScoreSubmit() {
	  return minScoreSubmit;
	}
	
	public String htmlResourceDir() {
	  return htmlResourceDir;
	}
	
}
