/*
  This file is part of JDasher.

  JDasher is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  JDasher is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with JDasher; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

  Copyright (C) 2006      Christopher Smowton <cs448@cam.ac.uk>

  JDasher is a port derived from the Dasher project; for information on
  the project see www.dasher.org.uk; for information on JDasher itself
  and related projects see www.smowton.net/chris

*/

package com.angelo.androidprova.core;

/**
 * Exception to be thrown by a CSettingsStore object when it attempts
 * to load a setting but is unable to find a record of it in its
 * backing store.
 * <p>
 * This exception is caught within CSettingsStore, and is never
 * seen by external classes.
 * <p>
 * Typically the SettingsStore responds to this problem by loading
 * a default setting.
 * <p>
 * The base implementation of CSettingsStore throws this for all
 * calls to LoadSetting, as it does not know about a backing store.
 */
public class CParameterNotFoundException extends Exception {
	/**
	 * Name of the parameter which couldn't be loaded.
	 */
	public String paramName;
	
	/**
	 * Creates a new ParameterNotFoundException.
	 * 
	 * @param p Parameter which couldn't be loaded
	 */
	public CParameterNotFoundException(String p) {
		this.paramName = p;
	}
}
