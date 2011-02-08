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
 * Event notifying components that Dasher has been paused / halted.
 * <p>
 * The Interface will dispatch this event at an appopriate time;
 * components should enter an idle state.
 */
public class CStopEvent extends CEvent {
	
	/**
	 * Creates a new Stop event
	 *
	 */
	public CStopEvent() {
	    m_iEventType = 5; // EV_STOP
	} 
}