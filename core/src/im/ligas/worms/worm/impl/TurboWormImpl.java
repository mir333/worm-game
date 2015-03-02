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
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.WormsConstants;
import im.ligas.worms.util.Utils;

/**
 * @author Miroslav Ligas
 */
public class TurboWormImpl extends BaseWormWithAbilityImpl {

	private final Sound sound;
	private Vector2 jumpStart;
	private Vector2 jumpEnd;
	private boolean jumped = false;

	public TurboWormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, id, keyLeft, keyRight, keyExecute);

		sound = Gdx.audio.newSound(Gdx.files.internal("speed.wav"));

		jumpStart = new Vector2();
		jumpEnd = new Vector2();
	}

	@Override
	public void execute() {
		if (isAbilityReady()) {
			jumpStart.x = head.x;
			jumpStart.y = head.y;
			this.grow(2 * WormsConstants.GROW_FACTOR);
			sound.play();
			jumpEnd.x = head.x;
			jumpEnd.y = head.y;
			jumped = true;
		}
		super.execute();
	}

	@Override
	public boolean calculateDead(Array<Array<Shape2D>> objects) {
		boolean result = super.calculateDead(objects);
		if (jumped) {
			Vector2 intersection = new Vector2();
			if (result ||
				Utils.checkSelfCollisions(jumpStart, jumpEnd, body) ||
				Utils.objectCollisions(jumpStart, jumpEnd, objects, intersection)) {
				result = dead = true;
				head = intersection;
				body.set(body.size - 2, intersection.x);
				body.set(body.size - 1, intersection.y);
			}
			jumped = false;
		}
		return result;
	}

	@Override
	public void dispose() {
		sound.dispose();
	}
}
