package im.ligas.worms;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ligasm on 2/8/15.
 */
public class Utils {

	public static float calculateStartAngle(Vector2 start) {
		float x = start.x - WormsGame.CENTER.x;
		float y = start.y - WormsGame.CENTER.y;
		float radius = MathUtils.atan2(x, y);
		float degrees = radius * MathUtils.radiansToDegrees;
		if ((x < 0 && y > 0) || (x > 0 && y < 0)) {
			degrees -= 180;
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

	public static boolean checkSelfCollisions(Vector2 head, Array<Float> line) {
		return checkCollision(head, line, true);
	}

	public static boolean checkOpponentCollisions(Vector2 head, Array<Float> line) {
		return checkCollision(head, line, false);
	}

	private static boolean checkCollision(Vector2 head, Array<Float> line, boolean self) {
		Vector2 start = new Vector2();
		Vector2 end = new Vector2();
		for (int i = 0; i < line.size - 2; i += 2) {
			start.x = line.get(i);
			start.y = line.get(i + 1);
			end.x = line.get(i + 2);
			end.y = line.get(i + 3);

			if (self && samePoint(end, head)) {
				continue;
			}
			if (Intersector.intersectSegmentCircle(start, end, head, 1)) {
				return true;
			}


		}
		return false;
	}

	private static boolean samePoint(Vector2 end, Vector2 head) {
		return MathUtils.isEqual(end.x, head.x, 1.5f) && MathUtils.isEqual(end.y, head.y, 1.5f);
	}
}
