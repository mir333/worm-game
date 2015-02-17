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

package im.ligas.worms.worm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.util.Utils;
import im.ligas.worms.WormsConstants;

/**
 * Created by ligasm on 2/8/15.
 */
public class Worm {
	private final String name;
	private Vector2 head;
	private Array<Float> body;
	private Heading heading;

	private int inputKeyLeft;
	private int inputKeyRight;
	private int inputKeyExecute;
	private boolean dead = false;
	private Color color;

	public Worm(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		body = new Array<Float>(WormsConstants.INIT_SIZE);
		body.add(start.x);
		body.add(start.y);
		body.add(start.x);
		body.add(start.y);
		this.head = start;
		heading = new Heading((int) Utils.calculateStartAngle(start));
		this.inputKeyLeft = keyLeft;
		this.inputKeyRight = keyRight;
		this.inputKeyExecute = keyExecute;
		this.color = color;
		this.name = name;
	}

	public int getInputKeyLeft() {
		return inputKeyLeft;
	}

	public int getInputKeyRight() {
		return inputKeyRight;
	}

	public Vector2 getHead() {
		return head;
	}

	public Array<Float> getBody() {
		return body;
	}

	public void turnLeft(boolean start) {
		if (start) {
			heading.turning--;
		} else {
			heading.turning++;
		}
	}

	public void turnRight(boolean start) {
		if (start) {
			heading.turning++;
		} else {
			heading.turning--;
		}
	}

	public void grow(float factor) {
		if (!dead) {
			if (heading.turning != 0) {
				if (heading.turning < 0) {
					heading.angle += 1;
				} else {
					heading.angle -= 1;
				}
			}

			head.x += (MathUtils.cosDeg(heading.angle) * factor);
			head.y += (MathUtils.sinDeg(heading.angle) * factor);

			if (heading.turning != 0) {
				body.add(head.x);
				body.add(head.y);
			} else {
				body.set(body.size - 2, head.x);
				body.set(body.size - 1, head.y);
			}
		}
	}

	public void setDead(boolean dead) {
		if (!this.dead) {
			this.dead = dead;
		}
	}

	public boolean isDead() {
		return dead;
	}

	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(color);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.polyline(Utils.convertToPrimitive(body));
		shapeRenderer.end();
	}

	@Override
	public String toString() {
		return name;
	}

	public String toDebugString() {
		return "Heading is = " + heading
			+ "\nX = " + head.x
			+ "\nY = " + head.y
			+ "\nSize = " + body.size;
	}

	public void extend() {
		body.ensureCapacity(WormsConstants.INIT_SIZE);
	}

	private class Heading {
		int angle;
		short turning;

		public Heading(int angle) {
			this.angle = angle;
		}
	}
}
