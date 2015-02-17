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
import com.badlogic.gdx.math.Vector2;
import im.ligas.worms.worm.WormWithAbility;

/**
 * @author Miroslav Ligas
 */
public class SniperWormImpl extends WormImpl implements WormWithAbility {
	private int inputKeyExecute;


	public SniperWormImpl(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		super(start, color, name, keyLeft, keyRight);
		this.inputKeyExecute = keyExecute;

	}

	@Override
	public int getInputKeyExecute() {
		return inputKeyExecute;
	}

	@Override
	public void execute() {

	}

}
