package com.kamenbaby.commons.beanuitl;


import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;


/**
 * <p>Standard {@link Converter} implementation that converts an incoming
 * String into a <code>java.math.BigDecimal</code> object, optionally using a
 * default value or throwing a {@link ConversionException} if a conversion
 * error occurs.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.6 $ $Date: 2003/10/09 20:43:16 $
 * @since 1.3
 */

public final class NumberConverter implements Converter {

	

    // ----------------------------------------------------------- Constructors


    /**
     * Create a {@link Converter} that will throw a {@link ConversionException}
     * if a conversion error occurs.
     */
    public NumberConverter() {}


    
    // --------------------------------------------------------- Public Methods


    /**
     * Convert the specified input object into an output object of the
     * specified type.
     *
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     *
     * @exception ConversionException if conversion cannot be performed
     *  successfully
     */
    public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
    	if (null==value) return null;
        
        if (value instanceof Number) {
            return (value);
        }

        String v;
        if (value instanceof String) v = (String)value;
        else v = value.toString();
    	v = v.trim();
    	
        if ("".equals(v) || "-".equals(v)) {
            return getNaN(type);
        }
        if ("true".equals(v)) v = "1";
        if ("false".equals(v)) v = "0";
        
        try {
        	if (Integer.class==type || int.class==type) return Integer.valueOf(v);
        	if (Long.class==type || long.class==type) return Long.valueOf(v);
        	if (Float.class==type || float.class==type) return Float.valueOf(v);
        	if (Short.class==type || short.class==type) return Short.valueOf(v);
        	if (Byte.class==type || byte.class==type) return new Byte(v);
        	if (BigDecimal.class==type) return new BigDecimal(v);
        	if (BigInteger.class==type) return new BigInteger(v);
        	return Double.valueOf(v);
        }
        catch (Exception e) {
            throw new ConversionException("Can't convert \""+v+"\" to "+type, e);
        }

    }


    public static boolean isNaN(Number n) {
    	if (null==n) return true;
    	Class<?> type = n.getClass();
    	if (Integer.class==type || int.class==type) return n.equals(Integer.MIN_VALUE);
    	if (Long.class==type || long.class==type) return n.equals(Long.MIN_VALUE);
    	if (Float.class==type || float.class==type) return n.equals(Float.MIN_VALUE);
    	if (Short.class==type || short.class==type) return n.equals(Short.MIN_VALUE);
    	if (Byte.class==type || byte.class==type) return n.equals(Byte.MIN_VALUE);
    	if (BigDecimal.class==type) return n.equals(BigDecimal.valueOf(Double.MIN_VALUE));
    	if (BigInteger.class==type) return n.equals(BigInteger.valueOf(Long.MIN_VALUE));
    	return Double.isNaN(n.doubleValue());
    }


    private static Number getNaN(Class<?> type) {
    	if (Integer.class==type || int.class==type) return Integer.MIN_VALUE;
    	if (Long.class==type || long.class==type) return Long.MIN_VALUE;
    	if (Float.class==type || float.class==type) return Float.MIN_VALUE;
    	if (Short.class==type || short.class==type) return Short.MIN_VALUE;
    	if (Byte.class==type || byte.class==type) return Byte.MIN_VALUE;
    	if (BigDecimal.class==type) return BigDecimal.valueOf(Double.MIN_VALUE);
    	if (BigInteger.class==type) return BigInteger.valueOf(Long.MIN_VALUE);
    	return Double.NaN;
    }
}
