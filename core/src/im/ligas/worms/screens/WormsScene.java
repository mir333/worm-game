package im.ligas.worms.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.Utils;
import im.ligas.worms.Worm;
import im.ligas.worms.WormsConstants;
import im.ligas.worms.WormsGame;

import static im.ligas.worms.WormsConstants.*;

public class WormsScene extends BaseScreen<WormsGame> {

	private static final Array<Vector2> startPositions = new Array<Vector2>(8) {{
		add(new Vector2(CENTER.x - 100, CENTER.y - 100));
		add(new Vector2(CENTER.x + 100, CENTER.y + 100));
		add(new Vector2(CENTER.x - 100, CENTER.y + 100));
		add(new Vector2(CENTER.x + 100, CENTER.y - 100));
	}};

	private ShapeRenderer shapeRenderer;

	private Array<Worm> worms;
	private short wormsCount;

	private int shapeRendererSize = 1;
	private boolean gameOver = false;

	public WormsScene(WormsGame game) {
		super(game);

		worms = new Array<Worm>(4);
		worms.add(new Worm(startPositions.get(0), Color.RED, "Red worm", Input.Keys.LEFT, Input.Keys.RIGHT));
		worms.add(new Worm(startPositions.get(1), Color.GREEN, "Green worm", Input.Keys.Q, Input.Keys.E));
		worms.add(new Worm(startPositions.get(2), Color.YELLOW,"Yellow worm", Input.Keys.J, Input.Keys.L));
		worms.add(new Worm(startPositions.get(3), Color.BLUE,"Blue worm", Input.Keys.Z, Input.Keys.C));

		wormsCount = (short) worms.size;

		shapeRenderer = new ShapeRenderer(INIT_SIZE);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glLineWidth(5);

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		game.batch.setProjectionMatrix(camera.combined);

		if (gameOver) {
			gameOver();
			return;
		}
		for (Worm worm : worms) {
			handleInput(worm);
		}

		for (Worm worm : worms) {
			autoExtendLine(worm, delta);
		}

		short dead = 0;
		for (int i = 0; i < worms.size; i++) {
			Worm worm = worms.get(i);
			if (worm.isDead() ||
				wallCollision(worm) ||
				selfCollision(worm) ||
				opponentCollision(worm, i, worms)
				) {
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

		printDebugData(worms);


	}

	private boolean opponentCollision(Worm worm, int currentWorm, Array<Worm> worms) {
		boolean dead = false;
		for (int i = 0; i < this.worms.size; i++) {
			if (i != currentWorm) {
				Array<Float> opponentBody = this.worms.get(i).getBody();
				dead = Utils.checkOpponentCollisions(worm.getHead(), opponentBody);
			}
			if (dead) {
				worm.setDead(dead);
				break;
			}
		}
		return worm.isDead();
	}

	private boolean selfCollision(Worm worm) {
		worm.setDead(Utils.checkSelfCollisions(worm.getHead(), worm.getBody()));
		return worm.isDead();
	}

	private boolean wallCollision(Worm worm) {
		Vector2 head = worm.getHead();
		worm.setDead(head.x < 0 || head.x > DIMENSION_X || head.y < 0 || head.y > DIMENSION_Y);
		return worm.isDead();
	}


	private void autoExtendLine(Worm worm, float delta) {
		worm.grow(GROW_FACTOR * delta);
	}

	private void printDebugData(Array<Worm> worms) {
		game.batch.begin();
		game.font.setColor(Color.GREEN);
		game.font.setScale(1);
		for (int i = 0; i < worms.size; i++) {
			game.font.drawMultiLine(game.batch, worms.get(i).toDebugString(), 100, 250 + (i * 50));
		}
		game.font.draw(game.batch, "Mem Native Heap " + Gdx.app.getNativeHeap() / 1048576 + "MB", 100, 140);
		game.font.draw(game.batch, "Mem Java Heap= " + Gdx.app.getJavaHeap() / 1048576 + "MB", 100, 120);
		game.batch.end();

/*		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.CYAN);
		sr.circle(CENTER.x, CENTER.y, 5);
		sr.end();*/

		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.CYAN);
		for (int i = 0; i < worms.size; i++) {
			Vector2 head = worms.get(i).getHead();
			sr.circle(head.x,head.y, WormsConstants.WORM_HEAD_SIZE);
		}
		sr.end();
	}

	private void gameOver() {
		Worm winner = null;
		for (Worm worm : worms) {
			if (!worm.isDead()) {
				winner = worm;
				break;
			}
		}

		game.setScreen(new GameOverScreen(game, winner));
		dispose();


	}


	private void handleInput(Worm worm) {
		if (Gdx.input.isKeyPressed(worm.getInputKeyLeft())) {
			worm.turnLeft();
		}

		if (Gdx.input.isKeyPressed(worm.getInputKeyRight())) {
			worm.turnRight();
		}
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
