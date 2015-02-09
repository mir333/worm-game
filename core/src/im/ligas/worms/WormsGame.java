package im.ligas.worms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class WormsGame extends ApplicationAdapter {

	public static final int INIT_SIZE = 10000;
	public static final int DIMENSION_X = 1024;
	public static final int DIMENSION_Y = 600;
	public static final Vector2 CENTER = new Vector2((DIMENSION_X / 2), (DIMENSION_Y / 2));
	public static final Array<Vector2> startPositions = new Array<Vector2>(8) {{
		add(new Vector2(CENTER.x - 100, CENTER.y - 100));
		add(new Vector2(CENTER.x + 100, CENTER.y + 100));
		add(new Vector2(CENTER.x - 100, CENTER.y + 100));
		add(new Vector2(CENTER.x + 100, CENTER.y - 100));
	}};


	private BitmapFont font;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	private Array<Worm> worms;
	private short wormsCount;

	private int shapeRendererSize = 1;
	private boolean gameOver= false;


	@Override
	public void create() {
		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, DIMENSION_X, DIMENSION_Y);

		worms = new Array<Worm>(4);
		worms.add(new Worm(startPositions.pop(), Color.RED, Input.Keys.LEFT, Input.Keys.RIGHT));
		worms.add(new Worm(startPositions.pop(), Color.GREEN, Input.Keys.Q, Input.Keys.E));
//		worms.add(new Worm(startPositions.pop(), Color.YELLOW, Input.Keys.J, Input.Keys.L));
//		worms.add(new Worm(startPositions.pop(), Color.BLUE, Input.Keys.Z, Input.Keys.C));

		wormsCount = (short) worms.size;

		shapeRenderer = new ShapeRenderer(INIT_SIZE);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(5);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		if (gameOver) {
			gameOver();
			return;
		}
		for (Worm worm : worms) {
			handleInput(worm);
		}

		for (Worm worm : worms) {
			autoExtendLine(worm);
		}

		short dead = 0;
		for (int i = 0; i < worms.size; i++) {
			if (worms.get(i).isDead()) {
				dead++;
			}
		}
		gameOver = (wormsCount - dead) < 2;


		for (Worm worm : worms) {
			try {
				worm.draw(shapeRenderer);
			} catch (ArrayIndexOutOfBoundsException x) {
				worm.extend();
				shapeRenderer.dispose();
				shapeRendererSize++;
				shapeRenderer = new ShapeRenderer(INIT_SIZE * shapeRendererSize);
			}
		}
		for (Worm worm : worms) {
			printDebugData(worm);
		}

	}


	private void autoExtendLine(Worm worm) {
		worm.grow(50 * Gdx.graphics.getDeltaTime());
	}

	private void printDebugData(Worm worm) {
		batch.begin();
		font.setColor(Color.GREEN);
		font.drawMultiLine(batch, worm.toString(), 100, 250);
		font.draw(batch, "Mem Native Heap " + Gdx.app.getNativeHeap() / 1048576 + "MB", 100, 140);
		font.draw(batch, "Mem Java Heap= " + Gdx.app.getJavaHeap() / 1048576 + "MB", 100, 120);
		batch.end();

/*		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.CYAN);
		sr.circle(CENTER.x, CENTER.y, 5);
		sr.end();*/
	}

	private void gameOver() {
		batch.setProjectionMatrix(camera.combined);
		batch.setColor(Color.RED);

		batch.begin();
		font.draw(batch, "GAME OVER", CENTER.x - 100, CENTER.y);
		font.setScale(3);
		font.setColor(Color.RED);
		batch.end();
	}


	private void handleInput(Worm worm) {
		if (Gdx.input.isKeyPressed(worm.getInputKeyLeft())) {
			worm.turnLeft();
		}

		if (Gdx.input.isKeyPressed(worm.getInputKeyRight())) {
			worm.turnRight();
		}
	}

}
