/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package im.ligas.worms;

/**
 * @author Miroslav Ligas
 */
public class GameSettings {
	private int selectedWorms = 0;
	private boolean specialAbilityEnabled = true;
	private boolean sound = true;
	private boolean music = true;

	public int getSelectedWorms() {
		return selectedWorms;
	}

	public void setSelectedWorms(int selectedWorms) {
		this.selectedWorms = selectedWorms;
	}

	public boolean isSpecialAbilityEnabled() {
		return specialAbilityEnabled;
	}

	public void setSpecialAbilityEnabled(boolean specialAbilityEnabled) {
		this.specialAbilityEnabled = specialAbilityEnabled;
	}

	public boolean isSound() {
		return sound;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}

	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}
}
