package com.kamenbaby.commons.beanuitl;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FieldHelper {

	private static final Logger log = LoggerFactory
			.getLogger(FieldHelper.class);
	
	public static Field getField(Class<?> clazz, String name) {
		if (null==clazz || clazz==Object.class) return null;
		Field f = null;
		try {
			f = clazz.getDeclaredField(name);
		}
		catch (Exception e) {
			f = getField(clazz.getSuperclass(), name);
		}
		return f;
	}
	public static Object getFieldValue(Object obj, String name) {
		if (null==obj || StringUtils.isEmpty(name)) return null;
		Field f = getField(obj.getClass(), name);
		if (null!=f) {
			if (!f.isAccessible()) f.setAccessible(true);
			try {
				return f.get(obj);
			}
			catch (Exception e) {
				log.error("Occur error when get value from field of "+name+" of "+obj.getClass(), e);
			}
		}
		return null;
	}
	public static void setFieldValue(Object obj, String name, Object value) {
		if (null==obj || StringUtils.isEmpty(name)) return;
		Field f = getField(obj.getClass(), name);
		if (null!=f) {
			if (!f.isAccessible()) f.setAccessible(true);
			try {
				f.set(obj,value);
			}
			catch (Exception e) {
				log.error("Occur error when set value to field of "+name+" of "+obj.getClass(), e);
			}
		}
	}
    
    /**
     * 取回clazz（包括父类）中所有类型为type的属性.
     * 
     * @param clazz 给定的类.
     * @param type 属性声明的类型.
     * 
     * @return 返回一个Map，其键为找到的属性对象，值为属性的值类型，如果该属性为非泛型类型，则为null.
     * 
     */
    public static Map<Field,Class<?>> getFields(Class<?> clazz,
        Class<?> type) {
        if (null==clazz || clazz==Object.class) return null;
        Map<Field,Class<?>> fs = new HashMap<Field,Class<?>>();
        pickFeilds(fs, clazz, type, null);
        Map<Type,Class<?>> atas = GenericHelper.pickTypeMapped(clazz,null);
        return getFields(clazz.getSuperclass(), type, fs, atas);
    }
    private static Map<Field,Class<?>> getFields(Class<?> clazz,
        Class<?> type, Map<Field,Class<?>> fs, Map<Type,Class<?>> atas) {
        if (null==clazz || clazz==Object.class) return fs;
        pickFeilds(fs, clazz, type, atas);
        GenericHelper.pickTypeMapped(clazz,atas);
        return getFields(clazz.getSuperclass(), type, fs, atas);
    }

    
    // --
    
    /**
     * 组识属性及其泛型类型（实参）的映射.
     * 
     * @param pocket 一个结果集，方法结束后将传回此集合.
     * @param target 主体类.
     * @param annoClass 标注类.
     * @param mapped 泛型形参与实参的映射.
     * 
     */
    private static void pickFeilds(Map<Field,Class<?>> pocket, Class<?> target, Class<?> type, Map<Type,Class<?>> mapped) {
        for (Field f: target.getDeclaredFields()) {
            if (null!=type) {
                if (type.isAssignableFrom(f.getType())) {
                    if (!f.isAccessible()) f.setAccessible(true);
                    Class<?> c;
                    Type t = null;
                    if (null!=mapped) t = mapped.get(f.getGenericType());
                    if (t instanceof Class) c = (Class<?>)t;
                    else c = f.getType();
                    pocket.put(f, c);
                }
            }
        }
    }
}
