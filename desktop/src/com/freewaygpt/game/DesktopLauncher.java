package com.freewaygpt.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1024, 640);
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setTitle("Freeway GPT");
		new Lwjgl3Application(new FreewayGPT(), config);
	}
}
