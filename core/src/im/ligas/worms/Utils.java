package im.ligas.worms;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ligasm on 2/8/15.
 */
public class Utils {

	public static float calculateStartAngle(Vector2 start){
		float x = start.x - WormsGame.CENTER.x;
		float y = start.y - WormsGame.CENTER.y;
		float radius = MathUtils.atan2(x, y);
		float degrees = radius * MathUtils.radiansToDegrees;
		if((x < 0 && y > 0) || (x > 0 && y < 0)){
			degrees -=180;
		}
		return degrees;
	}

	public static float[] convertToPrimitive(Array<Float> array) {
		float[] result = new float[array.size];
		for (int i = 0; i < array.size; i++) {
			result[i] = array.get(i);
		}
		return result;
	}

	public static boolean checkSelfCollisions(Array<Float> line) {
		return checkIntersections(line, line, true);
	}

	public static boolean checkCollisions(Array<Float> lineA, Array<Float> lineB) {
		return checkIntersections(lineA, lineB, false);
	}

	private static boolean checkIntersections(Array<Float> lineA, Array<Float> lineB, boolean same) {
		for (int i = 0; i < lineA.size - 2; i += 2) {
			float startXA = lineA.get(i);
			float startYA = lineA.get(i + 1);
			float endXA = lineA.get(i + 2);
			float endYA = lineA.get(i + 3);

			int j = 0;
			if (same) {
				if (lineB.size > i + 4) {
					j = i;
				} else {
					break;
				}
			}
			for (; j < lineB.size - 2; j += 2) {
				if (same) {
					if (i == j || i - 2 == j || i + 2 == j) {
						continue;
					}
				}
				float startXB = lineB.get(j);
				float startYB = lineB.get(j + 1);
				float endXB = lineB.get(j + 2);
				float endYB = lineB.get(j + 3);
				Vector2 intersection = new Vector2();
				if (isSegmentIntersection(startXA, startYA, endXA, endYA, startXB, startYB, endXB, endYB, intersection)) {
					return true;
				}

			}
		}

		return false;
	}

	private static boolean isSegmentIntersection(float p0_x, float p0_y, float p1_x, float p1_y,
										  float p2_x, float p2_y, float p3_x, float p3_y, Vector2 intersection) {
		float s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;

		float s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			if (intersection != null) {
				intersection.set(p0_x + (t * s1_x), p0_y + (t * s1_y));
			}
			return true;
		}
		return false; // No collision
	}
}
