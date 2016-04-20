/**
 * 
 */
package com.kamenbaby.commons.beanuitl;

/**
 * @author <a href="mailto:xiai.fei@gmail.com">xiai_fei</a>  
 *
 * @date 2014-2-28  下午6:17:54
 */
public class ClassUtils {

	public static String getLowerName(String name ){
		String first =name.substring(0,1);
		return first.toLowerCase()+name.substring(1, name.length());
		
	}
	
	public static String getClassBeanName(String name ){
		name = name.substring(name.lastIndexOf(".")+1, name.length());
		String first =name.substring(0,1);
		return first.toLowerCase()+name.substring(1, name.length());
		
	}
}
