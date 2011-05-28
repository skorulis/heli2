package com.skorulis.heli2.core;

public class Config {
	
	private String scoreURL;
	
	private Config() {
		
	}
	
	public static Config instance() {
		return devConfig();
	}
	
	private static Config devConfig() {
		Config ret = new Config();
		ret.scoreURL = "http://skorulis.com/score/Heli";
		return ret;
	}
	
	public String scoreURL() {
		return scoreURL;
	}
	
}
