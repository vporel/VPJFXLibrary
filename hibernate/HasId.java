package vplibrary.hibernate;


/**
 * Cette interface est impl�ment�e par les entit�s ayant un identifiant de type Integer
 * @author VPOREL-DEV
 *
 */
public interface HasId {
	/**
	 * 
	 * @return Valeur de l'identifiant
	 */
	public Integer getId();
	
	/**
	 * Modifier la valeur de l'identifiant
	 * @param id
	 */
	public void setId(Integer id);
}
