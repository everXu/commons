package com.kamenbaby.commons.beanuitl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Generic帮助类。
 *
 *
 *
 *
 * @author <a href="mailto:zhengduan@sina.com">zhengduan</a>
 *
 *
 */
public class GenericHelper {

    
    /**
     * 将指定目标类型所定义的泛型类型的形参与实参进行映射并装入容器pocket中.
     * 
     * <p>此方法可以根据目标类的层次进行递归调用.</p>
     * 
     * @param target 目标类.
     * @param pocket 容器,如果不提供，则自动创建一个.
     * 
     * @return 直接返回pocket.
     * 
     */
    public static Map<Type,Class<?>> pickTypeMapped(Class<?> target, Map<Type,Class<?>> pocket) {
        if (null==pocket) pocket = new HashMap<Type,Class<?>>();
        Type t = target.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)t;
            Class<?> c = (Class<?>)pt.getRawType();
            Type[] ts = c.getTypeParameters();
            if (null!=ts && ts.length>0) {
                mappingType(pocket,ts,pt.getActualTypeArguments());
            }
        }
        return pocket;
    }

    
    /**
     * 将形参与实参的映射关系保存到一个容器(Map)中.
     * 
     * @param pocket 容器.
     * @param ts 形参.
     * @param as 实参.
     * 
     */
    private static void mappingType(Map<Type,Class<?>> pocket, Type[] ts, Type[] as) {
        Type t,a;
        for (int i=0; i<ts.length; i++) {
            t = ts[i];
            if ((a=getMappingType(as,i)) instanceof Class) {
                pocket.put(t, (Class<?>)a);
            }
            else replaceMappingType(pocket, t, a);
        }
    }
    private static Type getMappingType(Type[] as, int i) {
        if (null==as || as.length<=i) return null;
        return as[i];
    }
    
    /**
     * 重新设置形参与实参的对应关系，并替换中间对应关系.
     * 
     * @param pocket 容器.
     * @param key 形参.
     * @param value 实参.
     * 
     */
    private static void replaceMappingType(Map<Type,Class<?>> pocket, Type key, Type value) {
        Map.Entry<Type,Class<?>> e;
        Class<?> c = null;
        for (Iterator<Map.Entry<Type,Class<?>>> i=pocket.entrySet().iterator(); i.hasNext();) {
            if ((e=i.next()).getKey()==value) {
                c = e.getValue();
                //i.remove(); 去掉中间映射，但这里暂时保留.
                break;
            }
        }
        if (null==c) {// 找映射如 <E extends Abc>
            if (value instanceof TypeVariable) {
                TypeVariable<?> tv = (TypeVariable<?>)value;
                Type[] ts = tv.getBounds();
                if (null!=ts && ts.length==1 && (ts[0] instanceof Class)) c = (Class<?>)ts[0]; 
            }
        }
        if (null!=c) pocket.put(key, c);
    }
}
