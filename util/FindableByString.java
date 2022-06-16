package VPLibrary.util;

import java.util.List;

/**
 * Impl�ment�e par les classes fournissant des �l�ments qui peuvent �tre retrouv�s � partir d'une chaine de caract�res
 * @author VPOREL-DEV
 *
 */
public interface FindableByString<ElementsClass> {
	
	/**
	 * Retrouver un �l�ment � partir d'une chaine de caract�re
	 * On peut donc parcourir tous les �lements en appelant � chaque fois la m�thode toString
	 * @param str
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ElementsClass findByString(String str) throws ElementByStringNotFoundException;
	
	public List<ElementsClass> alternativeChoices();
}
