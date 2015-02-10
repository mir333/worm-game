/**
 * Worms Game - Simple libGDX based vector game.
 * Copyright (C) 2015  Miroslav Ligas
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package im.ligas.worms.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import im.ligas.worms.WormsGame;

/**
 * @author Miroslav Ligas
 */
public class MainMenuScreen extends BaseScreen<WormsGame> {

	public MainMenuScreen(final WormsGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.setScale(2);
		game.font.setColor(Color.GREEN);
		game.font.draw(game.batch, "Welcome to Worms!!! ", 100, 150);
		game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new WormsScene(game));
			dispose();
		}

	}

}
