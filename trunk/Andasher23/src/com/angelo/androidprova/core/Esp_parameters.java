
package com.angelo.androidprova.core;

/**
 * Enumeration of possible String parameter references. See
 * CParamTables for a list and definitions.
 */
public class Esp_parameters implements EParameters {
	public static final Esp_parameters SP_ALPHABET_ID = new Esp_parameters(0, "ALPHABET_ID");
	  	public static final Esp_parameters SP_ALPHABET_1 = new Esp_parameters(1, "ALPHABET_1");
	  	public static final Esp_parameters SP_ALPHABET_2 = new Esp_parameters(2, "ALPHABET_2");
	  	public static final Esp_parameters SP_ALPHABET_3 = new Esp_parameters(3, "ALPHABET_3");
	  	public static final Esp_parameters SP_ALPHABET_4 = new Esp_parameters(4, "ALPHABET_4");
	  	public static final Esp_parameters SP_COLOUR_ID = new Esp_parameters(5, "COLOUR_ID");
	  	public static final Esp_parameters SP_DEFAULT_COLOUR_ID = new Esp_parameters(6, "DEFAULT_COLOUR_ID");
	  	public static final Esp_parameters SP_DASHER_FONT = new Esp_parameters(7, "DASHER_FONT");
	  	public static final Esp_parameters SP_SYSTEM_LOC = new Esp_parameters(8, "SYSTEM_LOC");
	  	public static final Esp_parameters SP_USER_LOC = new Esp_parameters(9, "USER_LOC");
	  	public static final Esp_parameters SP_GAME_TEXT_FILE= new Esp_parameters(10, "GAME_TEXT_FILE");
	  	public static final Esp_parameters SP_TRAIN_FILE = new Esp_parameters(11, "TRAIN_FILE");
	  	public static final Esp_parameters SP_SOCKET_INPUT_X_LABEL = new Esp_parameters(12, "SOCKET_INPUT_X_LABEL");
	  	public static final Esp_parameters SP_SOCKET_INPUT_Y_LABEL = new Esp_parameters(13, "SOCKET_INPUT_Y_LABEL");
	  	public static final Esp_parameters SP_INPUT_FILTER = new Esp_parameters(14, "INPUT_FILTER");
	  	public static final Esp_parameters SP_INPUT_DEVICE= new Esp_parameters(15, "INPUT_DEVICE");
	  	public static final Esp_parameters SP_LM_HOST = new Esp_parameters(16, "LM_HOST");
	  	public static final Esp_parameters SP_Filename = new Esp_parameters(17, "Filename");
	  	public static final Esp_parameters SP_Dictionary = new Esp_parameters(18, "Dictionary");
	  	public static final Esp_parameters SP_Phonetic = new Esp_parameters(19, "Phonetic");
	  

	public int value;
	public String name;
	public static final int NUMBER_OF_VALUES = 20;

	
	
		private Esp_parameters(int value, String name) {
			this.value = value;
			this.name = name;
		}
		


	  
}
