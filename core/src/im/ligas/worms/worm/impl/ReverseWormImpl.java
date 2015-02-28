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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author Miroslav Ligas
 */
public class ReverseWormImpl extends BaseWormWithAbilityImpl {

	private final Sound sound;
	protected int oldAngle;
	protected Vector2 oldHead;

	public ReverseWormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, id, keyLeft, keyRight, keyExecute);
		this.sound = Gdx.audio.newSound(Gdx.files.internal("reverse.wav"));
		this.oldHead = new Vector2(super.head);
		this.oldAngle = super.heading.angle + 180;
	}

	@Override
	public void execute() {
		if (isAbilityReady()) {
			sound.play();

			if (heading.turning == 0) {
				body.add(head.x);
				body.add(head.y);
			}

			Vector2 helpHead = new Vector2(head);
			int helpAngle = heading.angle;

			head = oldHead;
			heading.angle = oldAngle;
			reverse(body);

			oldHead = helpHead;
			oldAngle = helpAngle;
		}
		super.execute();
	}

	@Override
	protected void finalize() throws Throwable {
		sound.dispose();
	}

	private void reverse(Array<Float> body) {
		Array<Float> copy = new Array<Float>(body);
		body.clear();
		while (copy.size > 0) {
			Float y = copy.pop();
			Float x = copy.pop();
			body.add(x);
			body.add(y);
		}
	}
}
