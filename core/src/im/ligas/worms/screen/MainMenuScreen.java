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

package im.ligas.worms.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import im.ligas.worms.WormsGame;

import static im.ligas.worms.WormsConstants.*;

/**
 * @author Miroslav Ligas
 */
public class MainMenuScreen extends BaseScreen<WormsGame> {
	private final Vector3 touchPosition;
	private final Music music;
	private final Texture menuTexture;
	private final Texture wormTexture;
	private final Sprite[] controls = new Sprite[7];
	private final Sprite[] players = new Sprite[4];

	private Sprite help;

	public MainMenuScreen(final WormsGame game) {
		super(game);

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);

		menuTexture = new Texture(Gdx.files.internal("menu.png"));
		wormTexture = new Texture(Gdx.files.internal("worm.png"));

		/* Play */
		controls[0] = new Sprite(menuTexture, 0, 0, 400, 96);
		controls[0].setPosition(CENTER.x - 200, CENTER.y - 100);
		/* Quit */
		controls[1] = new Sprite(menuTexture, 0, 96, 400, 96);
		controls[1].setPosition(CENTER.x - 200, CENTER.y - 200);
		/* Ability mode */
		controls[2] = new Sprite(menuTexture, 256, 192, 64, 64);
		controls[2].setPosition(DIMENSION_X - 110, DIMENSION_Y - 110);
		/* Music on*/
		controls[3] = new Sprite(menuTexture, 0, 192, 64, 64);
		controls[3].setPosition(20, DIMENSION_Y - 110);
		/* Sound on*/
		controls[4] = new Sprite(menuTexture, 128, 192, 64, 64);
		controls[4].setPosition(20, DIMENSION_Y - 200);
		/* Music off*/
		controls[5] = new Sprite(menuTexture, 64, 192, 64, 64);
		controls[5].setPosition(20, DIMENSION_Y - 110);
		/* Sound off*/
		controls[6] = new Sprite(menuTexture, 192, 192, 64, 64);
		controls[6].setPosition(20, DIMENSION_Y - 200);

		players[0] = new Sprite(wormTexture, 0, 0, 96, 96);
		players[0].setPosition(CENTER.x - 200, CENTER.y - 0);

		players[1] = new Sprite(wormTexture, 96, 0, 96, 96);
		players[1].setPosition(CENTER.x - 100, CENTER.y - 0);

		players[2] = new Sprite(wormTexture, 192, 0, 96, 96);
		players[2].setPosition(CENTER.x, CENTER.y - 0);

		players[3] = new Sprite(wormTexture, 288, 0, 96, 96);
		players[3].setPosition(CENTER.x + 100, CENTER.y - 0);

		touchPosition = new Vector3();

		loadSettings(game);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchPosition.x = screenX;
		touchPosition.y = screenY;
		touchPosition.z = 0;
		Vector3 unprojectPosition = camera.unproject(touchPosition);

		for (int i = 0; i < players.length; i++) {
			if (spriteContains(players[i], unprojectPosition)) {
				selectSprite(players[i]);
			}
		}

		if (spriteContains(controls[0], unprojectPosition)) {
			game.gameSettings.setSelectedWorms(calculateSelectedWorms());
			game.setScreen(new WormsScene(game));
			dispose();
		} else if (spriteContains(controls[1], unprojectPosition)) {
			dispose();
			System.exit(0);
		} else if (spriteContains(controls[2], unprojectPosition)) {
			selectSprite(controls[2]);
			game.gameSettings.setSpecialAbilityEnabled(!game.gameSettings.isSpecialAbilityEnabled());
		} else if (spriteContains(controls[3], unprojectPosition)) {
			stopPlayMusic();
		} else if (spriteContains(controls[4], unprojectPosition)) {
			switchControlSprites(6, 4);
			game.gameSettings.setSound(!game.gameSettings.isSound());
		}

		return false;
	}

	@Override
	public void render(float delta) {
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.setScale(1);
		game.font.setColor(Color.GREEN);
		game.font.draw(game.batch, "Tap anywhere to begin!", CENTER.x - 100, 30);

		for (int i = 0; i < controls.length - 2; i++) {
			controls[i].draw(game.batch);
		}

		for (Sprite player : players) {
			player.draw(game.batch);
		}

		game.batch.end();
	}

	@Override
	public void show() {
		if (game.gameSettings.isMusic()) {
			music.play();
		}
		super.show();
	}

	@Override
	public void hide() {
		music.stop();
		super.hide();
	}

	@Override
	public void dispose() {
		super.dispose();
		menuTexture.dispose();
		wormTexture.dispose();
		music.dispose();
	}


	private void selectSprite(Sprite player) {
		if (MathUtils.isEqual(1, player.getColor().a, 0.01f)) {
			player.setColor(1, 1, 1, 0.2f);
		} else {
			player.setColor(1, 1, 1, 1);
		}
	}

	private int calculateSelectedWorms() {
		int encodedPlayers = 0;
		for (int i = 0; i < players.length; i++) {
			if (MathUtils.isEqual(1, players[i].getColor().a, 0.01f)) {
				encodedPlayers += Math.pow(2, i);
			}
		}
		return encodedPlayers;
	}

	private void stopPlayMusic() {
		switchControlSprites(5, 3);
		if (game.gameSettings.isMusic()) {
			game.gameSettings.setMusic(false);
			music.stop();
		} else {
			game.gameSettings.setMusic(true);
			music.play();
		}

	}

	private boolean spriteContains(Sprite sprite, Vector3 position) {
		Rectangle boundingRectangle = sprite.getBoundingRectangle();
		return boundingRectangle.contains(position.x, position.y);
	}

	private void loadSettings(WormsGame game) {
		int selectedWorms = ~game.gameSettings.getSelectedWorms();
		if ((selectedWorms & 1) == 1) {
			players[0].setColor(1, 1, 1, 0.2f);
		}
		if ((selectedWorms & 2) == 2) {
			players[1].setColor(1, 1, 1, 0.2f);
		}
		if ((selectedWorms & 4) == 4) {
			players[2].setColor(1, 1, 1, 0.2f);
		}
		if ((selectedWorms & 8) == 8) {
			players[3].setColor(1, 1, 1, 0.2f);
		}

		if (!game.gameSettings.isMusic()) {
			switchControlSprites(5, 3);
		}
		if (!game.gameSettings.isSound()) {
			switchControlSprites(6, 4);;
		}
		if (!game.gameSettings.isSpecialAbilityEnabled()) {
			selectSprite(controls[2]);
		}
	}

	private void switchControlSprites(int from, int to) {
		help = controls[to];
		controls[to] = controls[from];
		controls[from] = help;
	}

}
