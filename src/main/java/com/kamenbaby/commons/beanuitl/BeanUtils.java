/**
 * 
 */
package com.kamenbaby.commons.beanuitl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:xiai.fei@gmail.com">xiai_fei</a>  
 *
 * @date 2014-3-24  下午3:47:01
 */
public class BeanUtils {

	public static Map<String,Object>  convertToMap(Object object) throws IllegalAccessException{
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			setFieldToMap(object, map, field);
		}
		
		Field[] parentFiled = object.getClass().getSuperclass().getDeclaredFields();
		for (Field field : parentFiled) {
			 setFieldToMap(object, map, field);
		}
		return map;
	}
	
	/***
	 * 获取超类的属性的map
	 * @param object
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String,Object>  convertSuperClassToMap(Object object) throws IllegalAccessException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		Field[] parentFiled = object.getClass().getSuperclass().getDeclaredFields();
		for (Field field : parentFiled) {
			 setFieldToMap(object, map, field);
		}
		return map;
	}
	
	
	

	/**
	 * @param message
	 * @param map
	 * @param field
	 * @return
	 * @throws IllegalAccessException
	 */
	private static void setFieldToMap(Object object,Map<String, Object> map, Field field) throws IllegalAccessException {
		if(Modifier.isFinal(field.getModifiers())||Modifier.isTransient(field.getModifiers()))	return ;
		field.setAccessible(true);
		map.put(field.getName(), field.get(object));
		
	}
	
	
	public static void copyMapToBean(Map<String,Object> map , Object obj) throws Exception{
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
	}
}
