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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import im.ligas.worms.worm.WormWithAbility;

import static im.ligas.worms.WormsConstants.DEFAULT_COOL_DOWN;

/**
 * @author Miroslav Ligas
 */
public class BaseWormWithAbilityImpl extends WormImpl implements WormWithAbility {
	private final int inputKeyExecute;
	private boolean abilityReady;
	private long abilityExecuted;
	private Vector3 coolDownBarPos;

	protected BaseWormWithAbilityImpl(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, name, keyLeft, keyRight);
		this.inputKeyExecute = keyExecute;
		abilityReady = true;
		coolDownBarPos = new Vector3(1, 1, 1);
	}

	@Override
	public int getInputKeyExecute() {
		return inputKeyExecute;
	}

	@Override
	public void execute() {
		if (abilityReady) {
			abilityExecuted = TimeUtils.millis();
			abilityReady = false;
		}

	}

	@Override
	public void grow(float factor) {
		super.grow(factor);

		if (!abilityReady && TimeUtils.timeSinceMillis(abilityExecuted) > DEFAULT_COOL_DOWN) {
			abilityReady = true;
		}
	}

	@Override
	public void draw(ShapeRenderer shapeRenderer) {
		super.draw(shapeRenderer);
		if (!abilityReady) {
			float height = 200 - (200 * ((float) TimeUtils.timeSinceMillis(abilityExecuted) / DEFAULT_COOL_DOWN));
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(coolDownBarPos.x, coolDownBarPos.y, 10, coolDownBarPos.z * height);
			shapeRenderer.end();
		}
	}
	@Override
	public void setCoolDownBarPos(float x, float y, boolean up) {
		coolDownBarPos = new Vector3(x, y, 0);
		coolDownBarPos.z = up ? 1 : -1;

	}

	protected boolean isAbilityReady() {
		return abilityReady;
	}
}
