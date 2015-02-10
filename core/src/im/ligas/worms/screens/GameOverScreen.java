/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package im.ligas.worms.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
