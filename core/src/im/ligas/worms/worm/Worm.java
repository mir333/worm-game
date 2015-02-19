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

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * @author Miroslav Ligas
 */
public interface Worm {
	byte getId();

	int getInputKeyLeft();

	int getInputKeyRight();

	Vector2 getHead();

	Array<Float> getBody();

	void turnLeft(boolean start);

	void turnRight(boolean start);

	void grow(float factor);

	void setDead(boolean dead);

	boolean isDead();

	void draw(ShapeRenderer shapeRenderer);

	@Override
	String toString();

	String toDebugString();

	void extend();
}
