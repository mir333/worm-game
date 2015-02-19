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
import im.ligas.worms.WormsGame;
import im.ligas.worms.worm.Worm;

import static im.ligas.worms.WormsConstants.CENTER;

/**
 * @author Miroslav Ligas
 */
public class GameOverScreen extends BaseScreen<WormsGame> {
	private final Texture wormTexture;
	private final Music music;
	private Sprite sprite;

	public GameOverScreen(WormsGame game, Worm winner) {
		super(game);

		wormTexture = new Texture(Gdx.files.internal("worm.png"));
		music = Gdx.audio.newMusic(Gdx.files.internal("victory.mp3"));
		music.setLooping(true);


		if (winner != null) {
			sprite = new Sprite(wormTexture, winner.getId() * 96, 0, 96, 96);
			sprite.setPosition(CENTER.x - 48, CENTER.y-200);
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		game.setScreen(new MainMenuScreen(game));
		dispose();
		return false;
	}

	@Override
	public void render(float delta) {
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.mediumFont.setColor(Color.PURPLE);
		game.bigFont.setColor(Color.RED);
		game.bigFont.draw(game.batch, "GAME OVER", CENTER.x - 200, CENTER.y + 100);
		if (sprite != null) {
			game.mediumFont.draw(game.batch, "THE WINNER IS", CENTER.x - 170, CENTER.y);
			sprite.draw(game.batch);
		}

		game.batch.end();
	}

	@Override
	public void show() {
		if(game.gameSettings.isMusic()){
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
		wormTexture.dispose();
		music.dispose();
	}
}
