package im.ligas.worms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ligasm on 2/8/15.
 */
public class Worm {
	private Vector2 head;
	private Array<Float> body;
	private int heading;

	private int inputKeyLeft;
	private int inputKeyRight;
	private boolean changedDirection = true;
	private boolean dead = false;
	private Color color;

	public Worm(Vector2 start, Color color, int inputKeyLeft, int inputKeyRight) {
		body = new Array<Float>(WormsGame.INIT_SIZE);
		body.add(start.x);
		body.add(start.y);
		this.head = start;
		heading = (int) Utils.calculateStartAngle(start);
		this.inputKeyLeft = inputKeyLeft;
		this.inputKeyRight = inputKeyRight;
		this.color = color;
	}

	public int getInputKeyLeft() {
		return inputKeyLeft;
	}

	public int getInputKeyRight() {
		return inputKeyRight;
	}

	public int turnLeft() {
		changedDirection = true;
		return heading += 1;
	}

	public int turnRight() {
		changedDirection = true;
		return heading -= 1;
	}

	public void grow(float factor) {
		if (!dead) {
			head.x += (MathUtils.cosDeg(heading) * factor);
			head.y += (MathUtils.sinDeg(heading) * factor);

			if (changedDirection) {
				body.add(head.x);
				body.add(head.y);
			} else {
				body.set(body.size - 2, head.x);
				body.set(body.size - 1, head.y);
			}
			changedDirection = false;
			this.dead = Utils.checkSelfCollisions(head, body);
		}
	}

	public boolean isDead() {
		if (!dead) {
			dead = head.x < 0 || head.x > WormsGame.DIMENSION_X || head.y < 0 || head.y > WormsGame.DIMENSION_Y;
		}
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
		return "Heading is = " + heading
			+ "\nX = " + head.x
			+ "\nY = " + head.y
			+ "\nSize = " + body.size;
	}

	public void extend() {
		body.ensureCapacity(WormsGame.INIT_SIZE);
	}
}
