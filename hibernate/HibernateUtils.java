package vplibrary.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class HibernateUtils {
	
	/**
	 * @param entityClass
	 * @return
	 * @throws NotEntityClassException S'assurer que la classe entit� a l'annotation javax.persistence.Entity
	 */
	public static List<Field> getDatabaseFields(Class<?> entityClass) throws NotEntityClassException{
		if(entityClass.isAnnotationPresent(Entity.class)){
			List<Field> fields = new  ArrayList<>();
			for(Field field:entityClass.getDeclaredFields()) {
				field.setAccessible(true);
				if(
					field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Id.class) 
					|| field.isAnnotationPresent(ManyToOne.class)
					|| field.isAnnotationPresent(OneToOne.class) || (field.isAnnotationPresent(JoinColumn.class) && !field.isAnnotationPresent(ManyToMany.class))
				) {
					fields.add(field);
				}
			}
			return fields;
		}else {
			throw new NotEntityClassException("La classe "+entityClass+" n'est pas une class entit�");
		}
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException S'assurer que le champ existe bien dans la classe entit�
	 * @throws SecurityException
	 * @throws NoSuchDatabaseField S'assurer que le champ dans la classe a l'une des annotations javax.persistence.{Id, Column, ManyToOne, OneToOne, JoinColumn}
	 * @throws NotEntityClassException S'assurer que la classe entit� a l'annotation javax.persistence.Entity
	 */
	public static Field getDatabaseField(Class<?> entityClass, String fieldName) throws NoSuchFieldException, SecurityException, NoSuchDatabaseFieldException, NotEntityClassException {
		if(entityClass.isAnnotationPresent(Entity.class)){
			Field field = entityClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			if(
				field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Id.class) 
				|| field.isAnnotationPresent(ManyToOne.class)
				|| field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(JoinColumn.class)
			) {
				return field;
			}else {
				throw new NoSuchDatabaseFieldException("Le champ "+fieldName+" n'est pas reconnu dans la base donn�es (N'a pas l'annotation Column() dans la classe entit�");
			}
		}else {
			throw new NotEntityClassException("La classe "+entityClass+" n'est pas une class entit�");
		}
	}
	
	public static boolean existsDatabaseField(Class<?> entityClass, String fieldName) {
		
		try {
			getDatabaseField(entityClass, fieldName);
			return true;
		} catch (NoSuchFieldException | SecurityException | NoSuchDatabaseFieldException | NotEntityClassException e) {
			return false;
		}
		
	}
}
