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
package im.ligas.worms;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Miroslav Ligas
 */
public class WormsConstants {

	public static final int GROW_FACTOR = 20;
	public static final int DIMENSION_X = 1024;
	public static final int DIMENSION_Y = 600;
	public static final Vector2 CENTER = new Vector2((DIMENSION_X / 2), (DIMENSION_Y / 2));
	public static final int INIT_SIZE = 10000;
	public static final float COLLISION_DISTANCE = 0.5f;
}
