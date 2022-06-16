package vporel.util;

public class VPArray {
	/**
	 * Renvoie true si 'element' est dans le tableau 'array'
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean in(Object[] array, Object element) {
		for(Object el:array) {
			if(el.equals(element)) {
				return  true;
			}
		}
		return false;
	}
}
