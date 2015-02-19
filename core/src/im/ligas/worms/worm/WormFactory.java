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

package im.ligas.worms.worm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import im.ligas.worms.worm.impl.ReverseWormImpl;
import im.ligas.worms.worm.impl.SniperWormImpl;
import im.ligas.worms.worm.impl.TurboWormImpl;

/**
 * @author Miroslav Ligas
 */
public class WormFactory {

	public static WormWithAbility getSniperWorm(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		return new SniperWormImpl(start, color, name, keyLeft, keyRight, keyExecute);
	}

	public static WormWithAbility getTurboWorm(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		return new TurboWormImpl(start, color, name, keyLeft, keyRight, keyExecute);
	}

	public static WormWithAbility getSplitterWorm(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		return new SniperWormImpl(start, color, name, keyLeft, keyRight, keyExecute);
	}

	public static WormWithAbility getReverseWorm(Vector2 start, Color color, String name, int keyLeft, int keyRight, int keyExecute) {
		return new ReverseWormImpl(start, color, name, keyLeft, keyRight, keyExecute);
	}
}
