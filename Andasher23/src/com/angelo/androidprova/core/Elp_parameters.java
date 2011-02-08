
package com.angelo.androidprova.core;

/**
 * Enumeration of possible integer parameter references. See CParamTables for a
 * list and definitions.
 */
public class Elp_parameters implements EParameters {

	public int value;
	public String name;
	public static final int NUMBER_OF_VALUES = 43;

	private Elp_parameters(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static final Elp_parameters LP_ORIENTATION = new Elp_parameters(0,
			"ORIENTATION");

	public static final Elp_parameters LP_REAL_ORIENTATION = new Elp_parameters(
			1, "REAL_ORIENTATION");
	public static final Elp_parameters LP_MAX_BITRATE = new Elp_parameters(2,
			"MAX_BITRATE");
	public static final Elp_parameters LP_VIEW_ID = new Elp_parameters(3,
			"VIEW_ID");
	public static final Elp_parameters LP_LANGUAGE_MODEL_ID = new Elp_parameters(
			4, "LANGUAGE_MODEL_ID");
	public static final Elp_parameters LP_DASHER_FONTSIZE = new Elp_parameters(
			5, "DASHER_FONTSIZE");
	public static final Elp_parameters LP_UNIFORM = new Elp_parameters(6,
			"UNIFORM");
	public static final Elp_parameters LP_YSCALE = new Elp_parameters(7,
			"YSCALE");
	public static final Elp_parameters LP_MOUSEPOSDIST = new Elp_parameters(8,
			"MOUSEPOSDIST");
	public static final Elp_parameters LP_STOP_IDLETIME = new Elp_parameters(9,
			"STOP_IDLETIME");
	public static final Elp_parameters LP_TRUNCATION = new Elp_parameters(10,
			"TRUNCATION");
	public static final Elp_parameters LP_TRUNCATIONTYPE = new Elp_parameters(
			11, "TRUNCATIONTYPE");
	public static final Elp_parameters LP_LM_MAX_ORDER = new Elp_parameters(12,
			"LM_MAX_ORDER");
	public static final Elp_parameters LP_LM_EXCLUSION = new Elp_parameters(13,
			"LM_EXCLUSION");
	public static final Elp_parameters LP_LM_UPDATE_EXCLUSION = new Elp_parameters(
			14, "LM_UPDATE_EXCLUSION");
	public static final Elp_parameters LP_LM_ALPHA = new Elp_parameters(15,
			"LM_ALPHA");
	public static final Elp_parameters LP_LM_BETA = new Elp_parameters(16,
			"LM_BETA");
	public static final Elp_parameters LP_LM_MIXTURE = new Elp_parameters(17,
			"LM_MIXTURE");
	public static final Elp_parameters LP_MOUSE_POS_BOX = new Elp_parameters(
			18, "MOUSE_POS_BOX");
	public static final Elp_parameters LP_NORMALIZATION = new Elp_parameters(
			19, "NORMALIZATION");
	public static final Elp_parameters LP_LINE_WIDTH = new Elp_parameters(20,
			"LINE_WIDTH");
	public static final Elp_parameters LP_LM_WORD_ALPHA = new Elp_parameters(
			21, "LM_WORD_ALPHA");
	public static final Elp_parameters LP_USER_LOG_LEVEL_MASK = new Elp_parameters(
			22, "USER_LOG_LEVEL_MASK");
	public static final Elp_parameters LP_SPEED_DIVISOR = new Elp_parameters(
			23, "SPEED_DIVISOR");
	public static final Elp_parameters LP_ZOOMSTEPS = new Elp_parameters(24,
			"ZOOMSTEPS");
	public static final Elp_parameters LP_B = new Elp_parameters(25,
			"B");
	public static final Elp_parameters LP_S = new Elp_parameters(26,
			"S");
	public static final Elp_parameters LP_Z = new Elp_parameters(27,
			"Z");
	public static final Elp_parameters LP_R = new Elp_parameters(28,
			"R");
	public static final Elp_parameters LP_RIGHTZOOM = new Elp_parameters(29,
			"RIGHTZOOM");
	public static final Elp_parameters LP_BOOSTFACTOR = new Elp_parameters(30,
			"BOOSTFACTOR");
	public static final Elp_parameters LP_AUTOSPEED_SENSITIVITY = new Elp_parameters(
			31, "AUTOSPEED_SENSITIVITY");
	public static final Elp_parameters LP_SOCKET_PORT = new Elp_parameters(32,
			"AUTOSPEED_SENSITIVITY");
	public static final Elp_parameters LP_SOCKET_INPUT_X_MIN = new Elp_parameters(
			33, "SOCKET_INPUT_X_MIN");
	public static final Elp_parameters LP_SOCKET_INPUT_X_MAX = new Elp_parameters(
			34, "SOCKET_INPUT_X_MAX");
	public static final Elp_parameters LP_SOCKET_INPUT_Y_MIN = new Elp_parameters(
			35, "SOCKET_INPUT_Y_MIN");
	public static final Elp_parameters LP_SOCKET_INPUT_Y_MAX = new Elp_parameters(
			36, "SOCKET_INPUT_Y_MAX");
	public static final Elp_parameters LP_OX = new Elp_parameters(37,
			"OX");
	public static final Elp_parameters LP_OY = new Elp_parameters(38,
			"OY");
	public static final Elp_parameters LP_MAX_Y = new Elp_parameters(39,
			"MAX_Y");
	public static final Elp_parameters LP_INPUT_FILTER = new Elp_parameters(40,
			"INPUT_FILTER");
	public static final Elp_parameters LP_CIRCLE_PERCENT = new Elp_parameters(
			41, "CIRCLE_PERCENT");
	public static final Elp_parameters LP_TWO_BUTTON_OFFSET = new Elp_parameters(
			42, "TWO_BUTTON_OFFSET");

}
