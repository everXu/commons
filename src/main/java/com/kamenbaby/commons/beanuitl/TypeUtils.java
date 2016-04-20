/**
 * 
 */
package com.kamenbaby.commons.beanuitl;

/**
 * @author <a href="mailto:xiai.fei@gmail.com">xiai_fei</a>  
 *
 * @date 2014-3-3  下午10:52:09
 */
public class TypeUtils {

	 /**
		 * 校验参数是否为整型Integer
		 * @param o
		 * @return	true：是整型
		 */
		public static  boolean isByte(Object o){
			if(o!=null&&(o instanceof Byte)){
				return true;
			}
			return false ;
		}

		
		public static  boolean isShort(Object o){
			return o!=null?o instanceof Short:false;
		}
		
		public static  boolean isInteger(Object o){
			if(o!=null&&(o instanceof Integer)){
				return true;
			}
			return false ;
		}

		public static  boolean isLong(Object o){
			if(o!=null&&(o instanceof Long)){
				return true;
			}
			return false ;
		}
		
}
