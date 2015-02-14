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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import im.ligas.worms.GameSettings;
import im.ligas.worms.WormsGame;

import static im.ligas.worms.WormsConstants.CENTER;

/**
 * @author Miroslav Ligas
 */
public class MainMenuScreen extends BaseScreen<WormsGame> {
	private final Vector3 touchPosition;
	private Texture menuTexture;
	private Sprite quit;
	private Sprite start;
	private Sprite player1;
	private Sprite player2;
	private Sprite player3;
	private Sprite player4;
	private final Rectangle startRec;

	private final Rectangle quitRec;
	private final Rectangle player1Rec;
	private final Rectangle player2Rec;
	private final Rectangle player3Rec;
	private final Rectangle player4Rec;

	private int numberOfWorms;

	public MainMenuScreen(final WormsGame game) {
		super(game);
		menuTexture = new Texture(Gdx.files.internal("menu.png"));

		start = new Sprite(menuTexture, 0, 96, 400, 96);
		start.setPosition(CENTER.x - 200, CENTER.y - 100);
		startRec = new Rectangle(CENTER.x - 200, CENTER.y - 100, 400, 96);

		quit = new Sprite(menuTexture, 0, 192, 400, 96);
		quit.setPosition(CENTER.x - 200, CENTER.y - 200);
		quitRec = new Rectangle(CENTER.x - 200, CENTER.y - 200, 400, 96);

		player1 = new Sprite(menuTexture, 0, 0, 96, 96);
		player1.setPosition(CENTER.x - 200, CENTER.y - 0);
		player1.setColor(1, 1, 1, 0.2f);
		player1Rec = new Rectangle(CENTER.x - 200, CENTER.y - 0, 96, 96);

		player2 = new Sprite(menuTexture, 96, 0, 96, 96);
		player2.setPosition(CENTER.x - 100, CENTER.y - 0);
		numberOfWorms = 2;
		player2Rec = new Rectangle(CENTER.x - 100, CENTER.y - 0, 96, 96);

		player3 = new Sprite(menuTexture, 192, 0, 96, 96);
		player3.setPosition(CENTER.x, CENTER.y - 0);
		player3.setColor(1, 1, 1, 0.2f);
		player3Rec = new Rectangle(CENTER.x, CENTER.y - 0, 96, 96);

		player4 = new Sprite(menuTexture, 288, 0, 96, 96);
		player4.setPosition(CENTER.x + 100, CENTER.y - 0);
		player4.setColor(1, 1, 1, 0.2f);
		player4Rec = new Rectangle(CENTER.x + 100, CENTER.y - 0, 96, 96);

		touchPosition = new Vector3();
	}

	@Override
	public void render(float delta) {
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.setScale(1);
		game.font.setColor(Color.GREEN);
		game.font.draw(game.batch, "Tap anywhere to begin!", CENTER.x - 100, 30);

		start.draw(game.batch);
		quit.draw(game.batch);
		player1.draw(game.batch);
		player2.draw(game.batch);
		player2.draw(game.batch);
		player3.draw(game.batch);
		player4.draw(game.batch);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			touchPosition.x = Gdx.input.getX();
			touchPosition.y = Gdx.input.getY();
			touchPosition.z = 0;
			Vector3 unprojectPosition = camera.unproject(touchPosition);
			clearPlayers();
			if (player1Rec.contains(unprojectPosition.x, unprojectPosition.y)) {
				player1.setColor(1, 1, 1, 1);
				numberOfWorms = 1;
			} else if (player2Rec.contains(unprojectPosition.x, unprojectPosition.y)) {
				player2.setColor(1, 1, 1, 1);
				numberOfWorms = 2;
			} else if (player3Rec.contains(unprojectPosition.x, unprojectPosition.y)) {
				player3.setColor(1, 1, 1, 1);
				numberOfWorms = 3;
			} else if (player4Rec.contains(unprojectPosition.x, unprojectPosition.y)) {
				player4.setColor(1, 1, 1, 1);
				numberOfWorms = 4;
			} else if (startRec.contains(unprojectPosition.x, unprojectPosition.y)) {
				game.setScreen(new WormsScene(game));
				dispose();
			} else if (quitRec.contains(unprojectPosition.x, unprojectPosition.y)) {
				dispose();
				System.exit(0);
			}
		}

	}

	private void clearPlayers() {
		player1.setColor(1, 1, 1, 0.2f);
		player2.setColor(1, 1, 1, 0.2f);
		player3.setColor(1, 1, 1, 0.2f);
		player4.setColor(1, 1, 1, 0.2f);
	}

	@Override
	public void dispose() {
		super.dispose();
		menuTexture.dispose();
	}
}
