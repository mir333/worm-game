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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static im.ligas.worms.WormsConstants.DIMENSION_X;
import static im.ligas.worms.WormsConstants.DIMENSION_Y;

/**
 * @author Miroslav Ligas
 */
public class SniperWormImpl extends BaseWormWithAbilityImpl {

	private final Sound sound;
	private Projectile projectile;

	public SniperWormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, id, keyLeft, keyRight, keyExecute);

		this.sound = Gdx.audio.newSound(Gdx.files.internal("shoot.wav"));

		projectile = new Projectile();
	}

	@Override
	public void execute() {
		if (projectile.ready) {
			projectile.shoot(heading.angle);
			sound.play();
		}
	}

	@Override
	public void grow(float factor) {
		super.grow(factor);

		projectile.move(factor);

	}

	@Override
	public Array<Shape2D> getObstacles() {
		final Array<Shape2D> obsticles = super.getObstacles();
		obsticles.add(projectile.bullet);
		return obsticles;
	}

	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		super.draw(shapeRenderer);

		if (!projectile.ready) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.circle(projectile.bullet.x, projectile.bullet.y, projectile.bullet.radius);
			shapeRenderer.end();
		}
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

	private class Projectile {
		private int direction;
		private Circle bullet;
		private boolean ready;

		public Projectile() {
			this.bullet = new Circle();
			this.bullet.radius = 10;
			this.ready = true;
		}

		public void shoot(int direction) {
			this.bullet.setPosition(new Vector2(head));
			this.direction = direction;
			this.ready = false;
		}

		public void move(float factor) {
			if (!this.ready) {
				if (bullet.x > 0 && bullet.x < DIMENSION_X && bullet.y > 0 && bullet.y < DIMENSION_Y) {
					bullet.x += (MathUtils.cosDeg(direction) * factor * 5);
					bullet.y += (MathUtils.sinDeg(direction) * factor * 5);
				} else {
					this.ready = true;
				}
			}
		}
	}
}
