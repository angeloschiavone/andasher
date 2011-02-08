package com.angelo.androidprova.core;

/**
 * Enumeration of possible boolean parameter references. See CParamTables for a
 * list and definitions.
 */
public class Ebp_parameters implements EParameters {

	public int value;
	public String name;
	public static final int NUMBER_OF_VALUES = 42;

	private Ebp_parameters(int value, String name) {
		this.value = value;
		this.name = name;
	}
	

	public static final Ebp_parameters BP_DRAW_MOUSE_LINE = new Ebp_parameters(
			0, "DRAW_MOUSE_LINE");
	public static final Ebp_parameters BP_DRAW_MOUSE = new Ebp_parameters(1,"DRAW_MOUSE");
	public static final Ebp_parameters BP_SHOW_SLIDER = new Ebp_parameters(2,"SHOW_SLIDER");
	public static final Ebp_parameters BP_START_MOUSE = new Ebp_parameters(3,"START_MOUSE");
	public static final Ebp_parameters BP_START_SPACE = new Ebp_parameters(4,"START_SPACE");
	public static final Ebp_parameters BP_START_STYLUS = new Ebp_parameters(5,"START_STYLUS");
	public static final Ebp_parameters BP_STOP_IDLE = new Ebp_parameters(6,"STOP_IDLE");
	public static final Ebp_parameters BP_KEY_CONTROL = new Ebp_parameters(7,"KEY_CONTROL");
	public static final Ebp_parameters BP_CONTROL_MODE = new Ebp_parameters(8,"CONTROL_MODE");
	public static final Ebp_parameters BP_COLOUR_MODE = new Ebp_parameters(9,"COLOUR_MODE");
	public static final Ebp_parameters BP_MOUSEPOS_MODE = new Ebp_parameters(10,"MOUSEPOS_MODE");
	public static final Ebp_parameters BP_OUTLINE_MODE = new Ebp_parameters(11,"OUTLINE_MODE");
	public static final Ebp_parameters BP_PALETTE_CHANGE = new Ebp_parameters(
			12,"PALETTE_CHANGE");
	public static final Ebp_parameters BP_NUMBER_DIMENSIONS = new Ebp_parameters(
			13,"NUMBER_DIMENSIONS");
	public static final Ebp_parameters BP_EYETRACKER_MODE = new Ebp_parameters(
			14,"EYETRACKER_MODE");
	public static final Ebp_parameters BP_AUTOCALIBRATE = new Ebp_parameters(15,"AUTOCALIBRATE");
	public static final Ebp_parameters BP_DASHER_PAUSED = new Ebp_parameters(16,"DASHER_PAUSED");
	public static final Ebp_parameters BP_GAME_MODE = new Ebp_parameters(17,"GAME_MODE");
	public static final Ebp_parameters BP_TRAINING = new Ebp_parameters(18,"TRAINING");
	public static final Ebp_parameters BP_REDRAW = new Ebp_parameters(19,"REDRAW");
	public static final Ebp_parameters BP_LM_DICTIONARY = new Ebp_parameters(20,"LM_DICTIONARY");
	public static final Ebp_parameters BP_LM_LETTER_EXCLUSION = new Ebp_parameters(
			21,"LM_LETTER_EXCLUSION");
	public static final Ebp_parameters BP_AUTO_SPEEDCONTROL = new Ebp_parameters(
			22,"AUTO_SPEEDCONTROL");
	public static final Ebp_parameters BP_CLICK_MODE = new Ebp_parameters(23,"CLICK_MODE");
	public static final Ebp_parameters BP_LM_ADAPTIVE = new Ebp_parameters(24,"LM_ADAPTIVE");
	public static final Ebp_parameters BP_BUTTONONESTATIC = new Ebp_parameters(
			25,"BUTTONONESTATIC");
	public static final Ebp_parameters BP_BUTTONONEDYNAMIC = new Ebp_parameters(
			26,"BUTTONONEDYNAMIC");
	public static final Ebp_parameters BP_BUTTONMENU = new Ebp_parameters(27,"BUTTONMENU");
	public static final Ebp_parameters BP_BUTTONPULSING = new Ebp_parameters(28,"BUTTONPULSING");
	public static final Ebp_parameters BP_BUTTONSTEADY = new Ebp_parameters(29,"BUTTONSTEADY");
	public static final Ebp_parameters BP_BUTTONDIRECT = new Ebp_parameters(30,"BUTTONDIRECT");
	public static final Ebp_parameters BP_BUTTONFOURDIRECT = new Ebp_parameters(
			31,"BUTTONDIRECT");
	public static final Ebp_parameters BP_BUTTONALTERNATINGDIRECT = new Ebp_parameters(
			32,"BUTTONALTERNATINGDIRECT");
	public static final Ebp_parameters BP_COMPASSMODE = new Ebp_parameters(33,"BUTTONALTERNATINGDIRECT");
	public static final Ebp_parameters BP_SOCKET_INPUT_ENABLE = new Ebp_parameters(
			34,"SOCKET_INPUT_ENABLE");
	public static final Ebp_parameters BP_SOCKET_DEBUG = new Ebp_parameters(35,"SOCKET_DEBUG");
	public static final Ebp_parameters BP_OLD_STYLE_PUSH = new Ebp_parameters(
			36,"OLD_STYLE_PUSH");
	public static final Ebp_parameters BP_CIRCLE_START = new Ebp_parameters(37,"CIRCLE_START");
	public static final Ebp_parameters BP_GLOBAL_KEYBOARD = new Ebp_parameters(
			38,"GLOBAL_KEYBOARD");
	public static final Ebp_parameters BP_DELAY_VIEW = new Ebp_parameters(39,"DELAY_VIEW");
	public static final Ebp_parameters BP_LM_REMOTE = new Ebp_parameters(40,"LM_REMOTE");
	public static final Ebp_parameters BP_CONNECT_LOCK = new Ebp_parameters(41,"CONNECT_LOCK");

}
/*
 * 
 * public class Suit { private final String name; public static final Suit CLUBS
 * = new Suit("clubs"); public static final Suit DIAMONDS = new
 * Suit("diamonds"); public static final Suit HEARTS = new Suit("hearts");
 * public static final Suit SPADES = new Suit("spades");
 * 
 * private Suit(String name){ this.name =name; } public String toString(){
 * return name; } }
 */