package com.gddomenico.ih.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gddomenico.ih.invasorsHunt;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();


		config.title = invasorsHunt.TITLE;
		config.width = invasorsHunt.V_WIDTH * invasorsHunt.SCALE;
		config.height =  invasorsHunt.V_HEIGHT * invasorsHunt.SCALE;

		new LwjglApplication(new invasorsHunt(), config);
	}
}
