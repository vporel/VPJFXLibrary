package vplibrary.hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entité ayant la propriété <id>
 * @author VPOREL-DEV
 *
 */
@MappedSuperclass
public class EntityWithId extends Entity{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	} 

	@Override
	public String getKeyProperty() {
		// TODO Auto-generated method stub
		return "id";
	}
}
