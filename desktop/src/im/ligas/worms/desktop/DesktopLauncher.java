package im.ligas.worms.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import im.ligas.worms.WormsConstants;
import im.ligas.worms.WormsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Worms";
		config.width = WormsConstants.DIMENSION_X;
		config.height = WormsConstants.DIMENSION_Y;
		new LwjglApplication(new WormsGame(), config);
	}
}
