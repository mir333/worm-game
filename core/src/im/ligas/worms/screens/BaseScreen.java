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

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import im.ligas.worms.WormsConstants;
import im.ligas.worms.WormsGame;

/**
 * @author Miroslav Ligas
 */
public class BaseScreen<T>  implements Screen {

	final T game;

	OrthographicCamera camera;

	public BaseScreen(T game) {
		this.game = game;
		camera = new OrthographicCamera();

		camera.setToOrtho(false, WormsConstants.DIMENSION_X, WormsConstants.DIMENSION_Y);


	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
