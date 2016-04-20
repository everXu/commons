package com.kamenbaby.commons.beanuitl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实体工具.
 * 
 * @author <a href="mailto:zhengduan@sina.com">zhengduan</a>
 *
 */
public class EntityUtils {

	private static final Logger log = LoggerFactory
			.getLogger(EntityUtils.class);
	private final static java.util.Date NULL = new java.util.Date(0);
	
	public final static String FORM_FILE_CLASS = "org.apache.struts.upload.FormFile";
	
	private EntityUtils() {}

	/**
	 * 判断一个实体是否为瞬时对象(主要根据主键字段是否为非空).
	 * 
	 * @param 实体对象.
	 * @param keys 主键数组.
	 * 
	 */
	final public static boolean isTransient(Object entity, String... keys) {
		if (null==entity) return true;
		for (String k: keys) {
			if (isNull(getProperty(entity,k))) return true;
		}
		return false;
	}
	
	/**
	 * 判断一个实体是否为空(全部属性为空时表明该实体为空,忽略主键字段).
	 * 
	 * @param 实体对象.
	 * @param keys 主键数组.
	 * 
	 */
	final public static boolean isEmpty(Object entity, String... keys) {
		if (null==entity) return true;
		if ((null==keys || keys.length==0) && isNull(entity)) return true;
		@SuppressWarnings({"unchecked","rawtypes"})
		Map<String,Object> m = (Map)toMap(entity);
		for (Map.Entry<String,Object> me: m.entrySet()) {
			if (!contain(keys,me.getKey()) && !isNull(me.getValue())) return false;
		}
		return true;
	}

	
	final public static boolean containField(Object entity, String... fields) {
        if (null==entity) return false;
        if (null==fields || fields.length==0) return true;
        for (String field: fields) {
            try {
                if (entity instanceof Map && !(entity instanceof DynaBean)) {
                    if (!((Map<?,?>)entity).containsKey(field)) return false;
                }
                else if (null==PropertyUtils.getPropertyType(entity,field)) return false;
            }
            catch (Exception e) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * 清空指定实体的所有属性值.
	 * 
	 * @param 实体对象.
	 * 
	 */
	final public static void clear(Object entity) {
		if (null!=entity) {
			if (entity instanceof Map) ((Map<?,?>)entity).clear();
			else for (Object k: toMap(entity).keySet()) {
				try {
					PropertyUtils.setProperty(entity,(String)k,null);
				}
				catch (Exception e) {
					log.warn("Fatal to clear property \""+k+"\" of entity!", e);
				}
			}
		}
	}

	/**
	 * 取得实体的字段名列表.
	 * 
	 * @param entity 给定实体.
	 * 
	 * @return 返回实体的全部字段名.
	 * 
	 * 
	 */
	final public static Set<String> fieldSet(Object entity) {
		@SuppressWarnings({"unchecked","rawtypes"}) Map<String,?> m = (Map)toMap(entity);
		if (null==m) return new HashSet<String>();
		return m.keySet();
	}
	
	/**
	 * 将一个实体对象转换为一个Map，其中实体属性名为Map的键，值为Map的值.
	 * 
	 * @param entity 实体对象.
	 * 
	 * @return 返回所有属性的键值对.
	 * 
	 */
    final public static Map<String,Object> toMap(Object entity) {
		if (null==entity) return null;
		if (entity instanceof Map && !(entity instanceof DynaBean)) {
		    @SuppressWarnings({"unchecked"})
            Map<String,Object> map = (Map<String,Object>)entity;
            return map;
		}
		try {
		    Map<String,Object> map = PropertyUtils.describe(entity);
		    if (null!=map && !(entity instanceof DynaBean)) map.remove("class");
		    return map;
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * 取出指定实体中所有的非空属性并将其转换为一个Map，其中实体属性名为Map的键，值为Map的值.
	 * 
	 * @param entity 实体对象.
	 * 
	 * @return 返回取出的非空属性的键值对.
	 * 
	 */
	final public static Map<?,?> retrievePropertys(Object entity) {
		Map<?,?> m = toMap(entity);
		if (null==m) return null;
		Map<Object,Object> map = new HashMap<Object,Object>();
		for (Map.Entry<?,?> me: m.entrySet()) {
			if (!isNull(me.getValue())) map.put(me.getKey(),me.getValue());
		}
		return map;
	}
	
	final public static Object getProperty(Object entity, String name) {
		if (null==entity) return null;
		if (entity instanceof Map) return ((Map<?,?>)entity).get(name);
		try {
			return PropertyUtils.getProperty(entity, name);
		}
		catch (Exception e) {
			log.warn("No property \""+name+"\" found from entity!", e);
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	final public static void setProperty(Object entity, String name, Object value) {
		if (null!=entity) {
			if (entity instanceof Map) ((Map)entity).put(name,value);
			else try {
				BeanUtils.setProperty(entity, name, value);
			}
			catch (Exception e) {
				log.warn("Can't set value \""+value+"\" to property \""+name+"\"!", e);
			}
		}
	}
	
	final public static void copyProperty(Object src, Object dest, String... properties) {
		if (null!=src && null!=dest && null!=properties && properties.length>0) {
			for (String name: properties) {
				setProperty(dest, name, getProperty(src,name));
			}
		}
	}

	/**
	 * 判断指定对象是否为空值，包括<code>null</code>.
	 * 包括时间对象的零值，数字的最小值和字符串的空串等.
	 * 
	 * @param obj 被判断的对象，此值如果为时间对象零值、数字的最小值和字符串的空串等，均表示为空值.
	 * 
	 * @return 如果被判断的对象为广义的空值，则返回<code>true</code>，否则返回<code>false</code>.
	 * 
	 */
	final public static boolean isNull(Object obj) {
		if (null==obj) return true;
		if (obj instanceof Number) return NumberConverter.isNaN((Number)obj);
		if (obj instanceof java.util.Date) return NULL.equals(obj);
		return "".equals(obj);
	}

	/**
	 * 保存大文本对象前进行垃圾数据处理，以及保存文件名，属性，大小等数据.
	 * 
	 * @param entity 包含二进制文本的对象数据.
	 * @param fnf 文件名域.
	 * @param mtf mimeType类型域.
	 * @param fsf 文件大小域.
	 * @param exf 文件扩展名域.
	 * 
	 * @return 正常处理，返回true，垃圾数据，返回false.
	 * 
	 */
	final public static boolean prePersisting(Object entity, String fnf, String mtf, String fsf, String exf) {
		if (null==entity || (null==fnf && null==mtf && null==fsf && null==exf)) return false;
		try {
			Map<?,?> m = PropertyUtils.describe(entity);
			Object o;
			for (Map.Entry<?,?> me: m.entrySet()) {
				if (null!=(o=me.getValue()) && o instanceof java.sql.Blob) {
					if (!isTransient(o,"fileName")) {
						if (null!=fnf) try {BeanUtils.setProperty(entity, fnf, getProperty(o, "fileName"));} catch(Exception e) {}
						if (null!=mtf) try {BeanUtils.setProperty(entity, mtf, getProperty(o, "contentType"));} catch(Exception e) {}
						if (null!=fsf) try {BeanUtils.setProperty(entity, fsf, getProperty(o, "fileSize"));} catch(Exception e) {}
						if (null!=exf) {
							try {
								String s = BeanUtils.getProperty(o, "fileName");
								int i = s.lastIndexOf(".");
								if (i>-1) s = s.substring(i+1);
								BeanUtils.setProperty(entity, exf, s);
							}
							catch(Exception e) {}
						}
						return true;
					}
					else me.setValue(null);
				}
			}
			return false;
		}
		catch (Exception e) {return false;}
	}


	private static boolean contain(String[] array, String entry) {
		for (String s: array) {
			if (org.apache.commons.lang3.StringUtils.endsWithIgnoreCase(s,entry)) return true;
		}
		return false;
	}
}
