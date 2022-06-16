package vporel.hibernate;


/**
 * Cette interface est implémentée par les entités ayant un identifiant de type Integer
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
