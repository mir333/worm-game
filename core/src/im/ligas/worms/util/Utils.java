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

package im.ligas.worms.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.WormsConstants;

import static im.ligas.worms.WormsConstants.DIMENSION_X;
import static im.ligas.worms.WormsConstants.DIMENSION_Y;

/**
 * Created by ligasm on 2/8/15.
 */
public class Utils {

	public static float calculateStartAngle(Vector2 start) {
		float x = start.x - WormsConstants.CENTER.x;
		float y = start.y - WormsConstants.CENTER.y;
		float radius = MathUtils.atan2(x, y);
		float degrees = radius * MathUtils.radiansToDegrees;
		if ((x < 0 && y > 0) || (x > 0 && y < 0)) {
			degrees -= 180;
		}
		return degrees;
	}

	public static boolean wallCollision(Vector2 vector) {
		return vector.x < 0 || vector.x > DIMENSION_X || vector.y < 0 || vector.y > DIMENSION_Y;
	}

	public static float[] convertToPrimitive(Array<Float> array) {
		float[] result = new float[array.size];
		for (int i = 0; i < array.size; i++) {
			result[i] = array.get(i);
		}
		return result;
	}

	public static boolean checkSelfCollisions(Vector2 vector, Array<Float> line) {
		float[] primLine = convertToPrimitive(line);
		return checkCollision(vector, primLine, true);
	}

	public static boolean checkSelfCollisions(Vector2 start, Vector2 end, Array<Float> line) {
		float[] primLine = convertToPrimitive(line);
		return checkCollision(start, end, primLine, true, null);
	}

	public static boolean objectCollisions(Vector2 vector, Array<Array<Shape2D>> arrays) {
		for (Array<Shape2D> objects : arrays) {
			for (Shape2D object : objects) {
				if (object instanceof Circle) {
					Circle circle = (Circle) object;
					if (circle.contains(vector)) {
						return true;
					}
				} else if (object instanceof Polyline) {
					Polyline polyline = (Polyline) object;
					if (checkCollision(vector, polyline.getVertices(), false)) {
						return true;
					}

				}
			}
		}
		return false;
	}

	public static boolean objectCollisions(Vector2 start, Vector2 end, Array<Array<Shape2D>> arrays, Vector2 intersection) {
		Vector2 help = new Vector2();
		for (Array<Shape2D> objects : arrays) {
			for (Shape2D object : objects) {
				if (object instanceof Circle) {
					Circle circle = (Circle) object;
					help.x = circle.x;
					help.y = circle.y;
					if (Intersector.intersectSegmentCircle(start, end, help, circle.radius)) {
						Intersector.nearestSegmentPoint(start, end, help, intersection);
						return true;
					}
				} else if (object instanceof Polyline) {
					Polyline polyline = (Polyline) object;
					if (checkCollision(start, end, polyline.getVertices(), false, intersection)) {
						return true;
					}

				}
			}
		}
		return false;
	}

	private static boolean checkCollision(Vector2 head, float[] line, boolean self) {
		Vector2 start = new Vector2();
		Vector2 end = new Vector2();
		int upperBoundary = line.length - 2;

		if (self) {
			upperBoundary -= 10;
		}

		for (int i = 0; i < upperBoundary; i += 2) {
			start.x = line[i];
			start.y = line[i + 1];
			end.x = line[i + 2];
			end.y = line[i + 3];

			if (Intersector.distanceSegmentPoint(start, end, head) < WormsConstants.COLLISION_DISTANCE) {
				return true;
			}


		}
		return false;
	}

	private static boolean checkCollision(Vector2 segmentStart, Vector2 segmentEnd, float[] line, boolean self, Vector2 intersection) {
		Vector2 start = new Vector2();
		Vector2 end = new Vector2();
		int upperBoundary = line.length - 2;

		if (self) {
			upperBoundary -= 10;
		}

		for (int i = 0; i < upperBoundary; i += 2) {
			start.x = line[i];
			start.y = line[i + 1];
			end.x = line[i + 2];
			end.y = line[i + 3];

			if (Intersector.intersectSegments(start, end, segmentStart, segmentEnd, intersection)) {
				return true;
			}


		}
		return false;
	}

}
