package VPLibrary.util;

public class VPString{
	/**
	 * Put in uppercase the first letter of the string given
	 * @param str
	 * @return
	 */
	public static String ucfirst(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * Put in lowercase the first letter of the string given
	 * @param str
	 * @return
	 */
	public static String lcfirst(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * La terminaison ajout�e est en minuscule
	 * @param str Un mot
	 * @return Pluriel de la chaine en param�tre
	 */
	public static String plural(String str) {
		if(str.endsWith("y")) {
			return str.substring(0, str.length()-1)+"ies";
		}else {
			return str+"s";
		}
	}
}
