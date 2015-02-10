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
import im.ligas.worms.Worm;
import im.ligas.worms.WormsGame;

import static im.ligas.worms.WormsConstants.CENTER;

/**
 * @author Miroslav Ligas
 */
public class GameOverScreen extends BaseScreen<WormsGame> {


	private final Worm winner;

	public GameOverScreen(WormsGame game, Worm winner) {
		super(game);
		this.winner = winner;
	}

	@Override
	public void render(float delta) {
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.setScale(2);
		game.font.setColor(Color.RED);
		game.font.draw(game.batch, "GAME OVER", CENTER.x - 100, CENTER.y);
		game.font.draw(game.batch, "THE WINNER IS: " + winner, CENTER.x - 100, CENTER.y - 100);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new WormsScene(game));
			dispose();
		}
	}
}
