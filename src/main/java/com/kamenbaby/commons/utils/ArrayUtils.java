package com.kamenbaby.commons.utils;

/**
 * 数组工具.
 * 
 * @author <a href="mailto:zhengduan@sina.com">zhengduan</a>
 *
 */
public class ArrayUtils {


	private ArrayUtils() {super();}

	/**
	 * 向数组追加一个元素.
	 * 
	 * @param array 原数组.
	 * @param e 指定元素.
	 * 
	 * @return 返回追加元素后的数组.
	 * 
	 */
	public static Object[] add(Object[] array, Object e) {
		if (null==e) return array;
		if (null==array) return new Object[]{e};
		int length = array.length;
		Object[] os = new Object[length+1];
		System.arraycopy(array,0,os,0,length);
		os[length] = e;
		return os;
	}
	
	/**
	 * 合并两个数组.
	 * 
	 * @param array1 数组1.
	 * @param array2 数组2.
	 * 
	 * @return 返回合并后的数组.
	 * 
	 */
	public static Object[] add(Object[] array1, Object... array2) {
		if (null==array1 || array1.length<1) return array2;
		if (null==array2 || array2.length<1) return array1;
		Object[] os = new Object[array1.length+array2.length];
		System.arraycopy(array1,0,os,0,array1.length);
		System.arraycopy(array2,0,os,array1.length,array2.length);
		return os;
	}

	/**
	 * 向数组往指定位置插入指定元素.
	 * 
	 * @param array 原数组.
	 * @param e 指定元素.
	 * @param index 指定位置,如果此值小于零或大于原数组长度,则插到末尾.
	 * 
	 * @return 返回插入元素后的数组.
	 * 
	 */
	public static Object[] insert(Object[] array, Object e, int index) {
		if (null==e) return array;
		if (null==array) return array = new Object[]{e};
		int length = array.length;
		if (index<0 || index>length) index = length;
		Object[] os = new Object[length+1];
		System.arraycopy(array,0,os,0,index);
		System.arraycopy(array,index,os,index,length-index);
		os[index] = e;
		return os;
	}
	
	/**
	 * 向数组1的指定位置插入数组2.
	 * 
	 * @param array1 数组1.
	 * @param array2 数组2.
	 * @param index 指定位置,如果此值小于零或大于原数组长度,则插到末尾.
	 * 
	 * @return 返回插入元素后的数组.
	 * 
	 */
	public static Object[] insert(Object[] array1, Object[] array2, int index) {
		if (null==array1 || array1.length<1) return array2;
		if (null==array2 || array2.length<1) return array1;
		int length = array1.length;
		if (index<0 || index>length) index = length;
		int pos = array2.length+index;
		Object[] os = new Object[length+array2.length];
		System.arraycopy(array1,0,os,0,index);
		System.arraycopy(array1,index,os,pos,length-index);
		System.arraycopy(array2,0,os,index,array2.length);
		return os;
	}

    public static String join(Object[] array, String delim) {
        StringBuffer sb = null;
        if (null!=array) for (Object o: array) {
            if (null==sb) sb = new StringBuffer(o.toString());
            else sb.append(delim).append(o);
        }
        return null==sb? null: sb.toString();
    }
	
}
