package vplibrary.util;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ObjectToHashMap {
	public static HashMap<String, Object> convert(Object object){
		HashMap<String, Object> map = new HashMap<>();
		for(Field field:object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(object));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
}
