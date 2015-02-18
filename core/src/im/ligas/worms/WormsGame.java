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

package im.ligas.worms;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import im.ligas.worms.screen.MainMenuScreen;

/**
 * @author Miroslav Ligas
 */
public class WormsGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont mediumFont;
	public BitmapFont bigFont;
	public GameSettings gameSettings;

	@Override
	public void create() {
		batch = new SpriteBatch();
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fontbold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = fontGen.generateFont(parameter);
		parameter.size = 48;
		mediumFont = fontGen.generateFont(parameter);
		parameter.size = 72;
		bigFont = fontGen.generateFont(parameter);
		fontGen.dispose();
		gameSettings = new GameSettings();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(3);

		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
