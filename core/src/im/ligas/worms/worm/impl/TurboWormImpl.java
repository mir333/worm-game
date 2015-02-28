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
package im.ligas.worms.worm.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import im.ligas.worms.WormsConstants;

import static im.ligas.worms.WormsConstants.DEFAULT_COOL_DOWN;

/**
 * @author Miroslav Ligas
 */
public class TurboWormImpl extends BaseWormWithAbilityImpl {

	private final Sound sound;

	public TurboWormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, id, keyLeft, keyRight, keyExecute);

		sound = Gdx.audio.newSound(Gdx.files.internal("speed.wav"));
	}

	@Override
	public void execute() {
		if (isAbilityReady()) {
			this.grow(2 * WormsConstants.GROW_FACTOR);
			sound.play();
		}
		super.execute();
	}

	@Override
	public void dispose()  {
		sound.dispose();
	}
}
