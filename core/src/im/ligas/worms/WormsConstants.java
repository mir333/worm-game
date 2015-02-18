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

package im.ligas.worms;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Miroslav Ligas
 */
public class WormsConstants {

	public static final long DEFAULT_COOL_DOWN = 5000;
	public static final int GROW_FACTOR = 20;

	public static final int DIMENSION_X = 1024;
	public static final int DIMENSION_Y = 600;
	public static final Vector2 CENTER = new Vector2((DIMENSION_X / 2), (DIMENSION_Y / 2));

	public static final int INIT_SIZE = 10000;
	public static final float COLLISION_DISTANCE = 0.5f;
}
