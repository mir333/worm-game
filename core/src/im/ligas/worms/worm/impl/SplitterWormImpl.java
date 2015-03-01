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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import im.ligas.worms.util.Utils;

/**
 * @author Miroslav Ligas
 */
public class SplitterWormImpl extends BaseWormWithAbilityImpl {

	private final Sound sound;
	private Array<Branch> branches;
	private int activeHeads;

	public SplitterWormImpl(Vector2 start, Color color, byte id, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, id, keyLeft, keyRight, keyExecute);

		this.sound = Gdx.audio.newSound(Gdx.files.internal("split.wav"));

		branches = new Array<Branch>();
	}

	@Override
	public void execute() {
		if (activeHeads == 0) {
			branches.add(new Branch(-45 ,new Vector2(head)));
			branches.add(new Branch(45 ,new Vector2(head)));
			sound.play();
		}
	}

	@Override
	public boolean calculateDead(Array<Array<Shape2D>> objects) {
		boolean result = super.calculateDead(objects);

		for (int i = 0; i < branches.size; i++) {
			Branch branch = branches.get(i);
			branch.bDead = branch.bDead ||
				Utils.wallCollision(branch.bHead) ||
				Utils.checkSelfCollisions(branch.bHead, body) ||
				Utils.objectCollisions(branch.bHead, objects);
			if(!branch.bDead){
				for (int j = 0; j < branches.size; j++) {
					if(j!=i){
						if(Utils.checkSelfCollisions(branch.bHead, branches.get(j).bBody)){
							branch.bDead = true;
							break;
						}
					}
				}
			}
		}

		activeHeads = 0;
		for (Branch branch : branches) {
			if(!branch.bDead){
				activeHeads++;
			}
		}

		if(result && activeHeads > 0){
			for (Branch branch : branches) {
				if(!branch.bDead){
					head = branch.bHead;
					Array<Float> tmp = body;
					body = branch.bBody;
					branch.bBody = tmp;
					branch.bDead = true;
					heading.angle += branch.deviation;
					result = dead = false;
					break;
				}
			}
		}

		return result;
	}

	@Override
	public Array<Shape2D> getObstacles() {
		final Array<Shape2D> obsticles = super.getObstacles();
		for (Branch branch : branches) {
			obsticles.add(new Polyline(Utils.convertToPrimitive(branch.bBody)));
		}
		return obsticles;
	}

	@Override
	public void grow(float factor) {
		super.grow(factor);
		for (Branch branch : branches) {
			if(!branch.bDead) {
				branch.grow(factor);
			}
		}
	}

	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		super.draw(shapeRenderer);
		for (Branch branch : branches) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.polyline(Utils.convertToPrimitive(branch.bBody));
			shapeRenderer.end();
		}
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

	private class Branch {
		private boolean bDead;
		private int deviation;
		private Vector2 bHead;
		private Array<Float> bBody;

		public Branch(int deviation, Vector2 bHead) {
			this.bDead = false;
			this.deviation = deviation;
			this.bHead = bHead;
			this.bBody = new Array<Float>();
			this.bBody.add(bHead.x);
			this.bBody.add(bHead.y);
			this.bBody.add(bHead.x);
			this.bBody.add(bHead.y);
		}

		public void grow(float factor){
			if (!bDead) {
				bHead.x += (MathUtils.cosDeg(heading.angle + deviation) * factor);
				bHead.y += (MathUtils.sinDeg(heading.angle + deviation) * factor);

				if (heading.turning != 0) {
					bBody.add(bHead.x);
					bBody.add(bHead.y);
				} else {
					bBody.set(bBody.size - 2, bHead.x);
					bBody.set(bBody.size - 1, bHead.y);
				}
			}
		}
	}
}
