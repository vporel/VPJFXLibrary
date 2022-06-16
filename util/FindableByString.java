package vporel.util;

import java.util.List;

/**
 * Implémentée par les classes fournissant des éléments qui peuvent être retrouvés à partir d'une chaine de caractères
 * @author VPOREL-DEV
 *
 */
public interface FindableByString<ElementsClass> {
	
	/**
	 * Retrouver un élément à partir d'une chaine de caractère
	 * On peut donc parcourir tous les élements en appelant à chaque fois la méthode toString
	 * @param str
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ElementsClass findByString(String str) throws ElementByStringNotFoundException;
	
	public List<ElementsClass> alternativeChoices();
}
