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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.WormsGame;
import im.ligas.worms.util.Utils;
import im.ligas.worms.worm.Worm;
import im.ligas.worms.worm.WormFactory;
import im.ligas.worms.worm.WormWithAbility;

import static im.ligas.worms.WormsConstants.*;

public class WormsScene extends BaseScreen<WormsGame> {

	private static final Array<Vector2> START_POSITIONS = new Array<Vector2>(8) {{
		add(new Vector2(CENTER.x - 100, CENTER.y - 100));
		add(new Vector2(CENTER.x + 100, CENTER.y + 100));
		add(new Vector2(CENTER.x - 100, CENTER.y + 100));
		add(new Vector2(CENTER.x + 100, CENTER.y - 100));
	}};
	private final Music music;

	private ShapeRenderer shapeRenderer;

	private Array<Worm> worms;
	private byte wormsCount;

	private int shapeRendererSize;
	private boolean gameOver;

	public WormsScene(WormsGame game) {
		super(game);

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);

		int selectedWorms = game.gameSettings.getSelectedWorms();
		worms = new Array<Worm>(4);

		if ((selectedWorms & 1) == 1) {
			WormWithAbility turboWorm = WormFactory.getTurboWorm(new Vector2(START_POSITIONS.get(1)), Keys.Q, Keys.E, Keys.W);
			turboWorm.setCoolDownBarPos(0, CENTER.y, true);
			worms.add(turboWorm);
		}
		if ((selectedWorms & 2) == 2) {
			WormWithAbility sniperWorm = WormFactory.getSniperWorm(new Vector2(START_POSITIONS.get(0)), Keys.LEFT, Keys.RIGHT, Keys.DOWN);
			sniperWorm.setCoolDownBarPos(0, CENTER.y, false);
			worms.add(sniperWorm);
		}
		if ((selectedWorms & 4) == 4) {
			WormWithAbility reverseWorm = WormFactory.getReverseWorm(new Vector2(START_POSITIONS.get(2)), Keys.J, Keys.L, Keys.K);
			reverseWorm.setCoolDownBarPos(DIMENSION_X - 10, CENTER.y, true);
			worms.add(reverseWorm);
		}
		if ((selectedWorms & 8) == 8) {
			WormWithAbility splitterWorm = WormFactory.getSplitterWorm(new Vector2(START_POSITIONS.get(3)), Keys.Z, Keys.C, Keys.X);
			splitterWorm.setCoolDownBarPos(DIMENSION_X - 10, CENTER.y, false);
			worms.add(splitterWorm);
		}

		wormsCount = (byte) worms.size;

		shapeRenderer = new ShapeRenderer(INIT_SIZE);

		shapeRendererSize = 1;
		gameOver = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		handdleImput(keycode, true);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		handdleImput(keycode, false);
		return false;
	}

	@Override
	public void render(float delta) {
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		game.batch.setProjectionMatrix(camera.combined);

		if (gameOver) {
			gameOver();
			return;
		}

		for (Worm worm : worms) {
			worm.grow(GROW_FACTOR * delta);
		}

		byte dead = 0;
		for (int i = 0; i < worms.size; i++) {
			Worm worm = worms.get(i);
			if (worm.isDead() ||
				wallCollision(worm) ||
				selfCollision(worm) ||
				opponentCollision(worm, i, worms)
				) {
				dead++;
			}
		}
		gameOver = (wormsCount - dead) < 2;


		for (Worm worm : worms) {
			try {
				worm.draw(shapeRenderer);
			} catch (ArrayIndexOutOfBoundsException x) {
				worm.extend();
				shapeRenderer.dispose();
				shapeRendererSize++;
				shapeRenderer = new ShapeRenderer(INIT_SIZE * shapeRendererSize);
			}
		}

//		printDebugData(worms);
	}

	@Override
	public void show() {
		if(game.gameSettings.isMusic()) {
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
		shapeRenderer.dispose();
		music.dispose();
		for (Worm worm : worms) {
			worm.dispose();
		}
	}


	private boolean opponentCollision(Worm worm, int currentWorm, Array<Worm> worms) {
		boolean dead = false;
		for (int i = 0; i < worms.size; i++) {
			if (i != currentWorm) {
				Array<Float> opponentBody = worms.get(i).getBody();
				dead = Utils.checkOpponentCollisions(worm.getHead(), opponentBody);
			}
			if (dead) {
				worm.setDead(dead);
				break;
			}
		}
		return worm.isDead();
	}

	private boolean selfCollision(Worm worm) {
		worm.setDead(Utils.checkSelfCollisions(worm.getHead(), worm.getBody()));
		return worm.isDead();
	}

	private boolean wallCollision(Worm worm) {
		Vector2 head = worm.getHead();
		worm.setDead(head.x < 0 || head.x > DIMENSION_X || head.y < 0 || head.y > DIMENSION_Y);
		return worm.isDead();
	}

	private void printDebugData(Array<Worm> worms) {
		game.batch.begin();
		game.font.setColor(Color.GREEN);
		game.font.setScale(1);
		for (int i = 0; i < worms.size; i++) {
			game.font.drawMultiLine(game.batch, worms.get(i).toDebugString(), 100, 250 + (i * 50));
		}
		game.font.draw(game.batch, "Mem Native Heap " + Gdx.app.getNativeHeap() / 1048576 + "MB", 100, 140);
		game.font.draw(game.batch, "Mem Java Heap= " + Gdx.app.getJavaHeap() / 1048576 + "MB", 100, 120);
		game.batch.end();
	}

	private void gameOver() {
		Worm winner = null;
		for (Worm worm : worms) {
			if (!worm.isDead()) {
				winner = worm;
				break;
			}
		}

		game.setScreen(new GameOverScreen(game, winner));
		dispose();
	}

	private void handdleImput(int keycode, boolean startEnd) {
		for (Worm worm : worms) {
			if (startEnd && worm instanceof WormWithAbility) {
				WormWithAbility wormWithAbility = (WormWithAbility) worm;
				if (keycode == wormWithAbility.getInputKeyExecute()) {
					wormWithAbility.execute();
					break;
				}
			}

			if (keycode == worm.getInputKeyLeft()) {
				worm.turnLeft(startEnd);
				break;
			}
			if (keycode == worm.getInputKeyRight()) {
				worm.turnRight(startEnd);
				break;
			}
		}

	}

}
