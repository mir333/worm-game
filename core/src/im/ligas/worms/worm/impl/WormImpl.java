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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.WormsConstants;
import im.ligas.worms.util.Utils;
import im.ligas.worms.worm.Worm;

/**
 * Created by ligasm on 2/8/15.
 */
public class WormImpl implements Worm {
	private final byte id;
	protected Vector2 head;
	protected Array<Float> body;
	protected Heading heading;

	private int inputKeyLeft;
	private int inputKeyRight;
	private boolean dead = false;
	private Color color;

	public WormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight) {
		body = new Array<Float>(WormsConstants.INIT_SIZE);
		body.add(start.x);
		body.add(start.y);
		body.add(start.x);
		body.add(start.y);
		this.head = start;
		heading = new Heading((int) Utils.calculateStartAngle(start));
		this.inputKeyLeft = keyLeft;
		this.inputKeyRight = keyRight;
		this.color = color;
		this.id = id;
	}

	@Override
	public byte getId() {
		return id;
	}

	@Override
	public int getInputKeyLeft() {
		return inputKeyLeft;
	}

	@Override
	public int getInputKeyRight() {
		return inputKeyRight;
	}

	@Override
	public Vector2 getHead() {
		return head;
	}

	@Override
	public Array<Float> getBody() {
		return body;
	}

	@Override
	public void turnLeft(boolean start) {
		if (start) {
			heading.turning--;
		} else {
			heading.turning++;
		}
	}

	@Override
	public void turnRight(boolean start) {
		if (start) {
			heading.turning++;
		} else {
			heading.turning--;
		}
	}

	@Override
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

	@Override
	public void setDead(boolean dead) {
		if (!this.dead) {
			this.dead = dead;
		}
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		shapeRenderer.setColor(color);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.polyline(Utils.convertToPrimitive(body));
		shapeRenderer.end();
	}

	@Override
	public String toDebugString() {
		return "Heading is = " + heading
			+ "\nX = " + head.x
			+ "\nY = " + head.y
			+ "\nSize = " + body.size;
	}

	@Override
	public void extend() {
		body.ensureCapacity(WormsConstants.INIT_SIZE);
	}

	protected class Heading {
		int angle;
		short turning;

		public Heading(int angle) {
			this.angle = angle;
		}
	}
}
