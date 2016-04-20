/**
 * 
 */
package com.kamenbaby.commons.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:xiai.fei@gmail.com">xiai_fei</a>  
 *
 * @date 2013-10-13  下午4:05:17
 */
public class DateUtils {
	
	private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 *  字符串转日期
	 *  
	 * @param content  字符串日期
	 * @return  日期
	 */
	public static java.sql.Date stringToDate(String content)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

		java.sql.Date date =null;
			try {
				//FIXME 这里强转不知道会不会有问题
				date = new java.sql.Date(sf.parse(content).getTime());
			} catch (ParseException e) {
				log.error("日期格式强转异常", e);
			}
		return date;
	}
	
	
	/**
	 *  字符串转日期
	 *  
	 * @param content  字符串日期
	 * @return  日期
	 */
	public static Date strToDate(String content)
	{
		SimpleDateFormat sf =null;
		if( content.indexOf( "-")>0 ){
			sf= new SimpleDateFormat("yyyy-MM-dd");
		}else if( content.indexOf( "/")>0 ){
    	   sf= new SimpleDateFormat("MM/dd/yyyy");
		}else{
    	   sf= new SimpleDateFormat("yyyyMMdd");
		}
		Date date =null;
			try {
				//FIXME 这里强转不知道会不会有问题
				date = (Date) sf.parse(content);
			} catch (ParseException e) {
				log.error("日期格式强转异常", e);
			}
		return date;
	}
	

	/***
	 * 本周最后一天
	 * @return
	 */
	public static String getWeekLastDate(){
		Calendar calendar =Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);//这个是从周日算起第一天的，上面的设置对这个没有用
		calendar.setTime(new Date());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1; //这个是从周日算起第一天的，但是这个设置没有用
		if(dayOfWeek==0)dayOfWeek=7; 
		calendar.add(Calendar.DATE, 7-dayOfWeek);
		return df.format(calendar.getTime());
	}
	
	/***
	 * 本月最后一天
	 * @return
	 */
	public static String getMonthLastDate(){
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(new Date());
		int month = calendar.get(Calendar.MONTH)+1;
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -1);
		return df.format(calendar.getTime());
	}
	
	/***
	 * 本月第一天
	 * @return
	 */
	public static String getMonthFirstStrDate(){
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(new Date());
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, -dayOfMonth+1);
		return df.format(calendar.getTime());
	}
	
	
	/***
	 * 本月第一天
	 * @return
	 */
	public static Date getMonthFirstDate(){
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(new Date());
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, -dayOfMonth+1);
		return calendar.getTime();
	}
	
	/***
	 * 获取当天零点
	 * @return
	 */
	public static Date getTodayZeorHour(){
		Calendar calendar =Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 将日期类型转换成字符串型
	 * @param date
	 * @return
	 */
	/**
	 * 从输入的date获取该日期的下一天
	 * @param date    输入的日期
	 * @return        输入日期的一下天
	 */
	public static String getNextDate(Object obj )
	{
		 Calendar c = Calendar.getInstance();
	        Date date = null;
	        try {
	            date = new SimpleDateFormat("yyyyMMdd").parse((String)obj);
	        } catch (ParseException e) {
	        	log.error("获取前一天日期出错", e);
	        }
	        c.setTime(date);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day + 1);

	        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c
	                .getTime());
	        return dayBefore;
	}
	
	/**
	 * 从输入的date获取该日期的下一天
	 * @param date    输入的日期
	 * @return        输入日期的一下天
	 */
	public static Date getNextDate(Date date)
	{
		if(date==null)  return null ;
	    return  new Date(date.getTime()+8640000);
	}
	
	
	/**
	 * 将日期类型转换成字符串型yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		if(date ==null){
			return null;
		}
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	/**
	 * 将日期类型转换成字符串型yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		if(date ==null){
			return null;
		}
		Format format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param str   指定日期
	 * @return
	 */
	public static String getBeforeDate(String str)
	{
		 Calendar c = Calendar.getInstance();
	        Date date = null;
	        try {
	            date = new SimpleDateFormat("yyyyMMdd").parse(str);
	        } catch (ParseException e) {
	        	log.error("获取前一天日期出错", e);
	        }
	        c.setTime(date);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - 1);

	        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c
	                .getTime());
	        return dayBefore;
	}
	
	
	public static Date getBeforeMinute(Date date , int minute)	{
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minute);
        return c.getTime();
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param str   指定日期
	 * @return
	 */
	public static String getBeforeDate(Date date)
	{
		 Calendar c = Calendar.getInstance();
//	        Date date = null;
//	        try {
//	            date = new SimpleDateFormat("yyyyMMdd").parse(str);
//	        } catch (ParseException e) {
//	        	log.error("获取前一天日期出错", e);
//	        }
	        c.setTime(date);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - 1);

	        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
	                .getTime());
	        return dayBefore;
	}
	
	/**
	 * 获取指定日期的前一天
	 * @param str   指定日期
	 * @return
	 */
	public static String getBeforeDateTime(Date date)
	{
		 Calendar c = Calendar.getInstance();
//	        Date date = null;
//	        try {
//	            date = new SimpleDateFormat("yyyyMMdd").parse(str);
//	        } catch (ParseException e) {
//	        	log.error("获取前一天日期出错", e);
//	        }
	        c.setTime(date);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - 1);

	        String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c
	                .getTime());
	        return dayBefore;
	}
	

	/**
	 * 获取系统日期
	 * @param str   指定日期
	 * @return
	 */
	public static Date getSystemDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	 	
	/**
	 * 获取指定日期的前后的天数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDate(Date date,int days) {
		if(date==null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + days);
		return c.getTime();
	}

	

	
	
	
	/**
	 * 将日期类型转换成指定格式的字符串
	 * @param date   日期
	 * @parm  fmt  格式
	 * @return
	 */
	public static String date2Str(Date date,String fmt){
		Format format = new SimpleDateFormat(fmt);
		return format.format(date);
	}
	
	
	public static String date2Str(Date date){
		return format.format(date);
	}

	
		
	
	
	/**
	 *  字符串转日期
	 *  
	 * @param content  字符串日期
	 * @return  日期
	 */
	public static Date strFormatToDate(String content){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date =null;
			try {
				date = (Date) sf.parse(content);
			} catch (ParseException e) {
				log.error("日期格式强转异常", e);
			}
		return date;
	}
	public static Date strFormatToDate(String content,String fmt){
		SimpleDateFormat sf = new SimpleDateFormat(fmt);
		Date date =null;
			try {
				date = (Date) sf.parse(content);
			} catch (ParseException e) {
				log.error("日期格式强转异常", e);
			}
		return date;
	}
	/**
	 * 统一两个时间之间的时长(分钟)
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getMinuteBy2Time(String time1, String time2){
		  long quot = 0;
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  try {
		   Date date1 = ft.parse( time1 );
		   Date date2 = ft.parse( time2 );
		   quot = date1.getTime() - date2.getTime();
		   quot = quot /1000/ 60 ;
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return quot;
	}
	
	
	/**
	 * 统一两个时间之间的时长(秒钟)
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getSecondBy2Time(String time1, String time2){
		  long quot = 0;
		  SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  try {
		   Date date1 = ft.parse( time1 );
		   Date date2 = ft.parse( time2 );
		   quot = date1.getTime() - date2.getTime();
		   quot = quot /1000 ;
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return quot;
	}
	
	/**
	 * 活动当前日期的前num天
	 * @return	格式化后的字符串日期    yyyy-MM-dd
	 */
	public static String getCurDateBefore(int num){
		 Calendar c = Calendar.getInstance();
	      Date date = new Date();
	        c.setTime(date);
	      int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - num);  //注意
	     String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
	                .getTime());
	    return dayBefore;
	}
	
	/**
	 *   获取指定日期的下一天，传入的参数是timestamp类型
	 * 
	 * @param st
	 * @return  timestamp  下一天
	 */
	public static Timestamp getNextDate(Timestamp st){
		 Calendar c = Calendar.getInstance();
	        c.setTime(st);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day +1);
	        return new Timestamp( c.getTime().getTime());
	}
	
	/***
	 * 获取指定该日期的下N天
	 * @param date
	 * @param num  正数为后N天，负数为前N天
	 * @return
	 * @throws ParseException
	 */
	public static  String getNextDate(String date,int num) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(date));
		calendar.add(Calendar.DATE, num);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 检查传入的字符串是否满足特定的日期格式
	 * @param date	输入的日期字符串
	 * @return	
	 */
	public static  boolean checkDateFormat(String date,String pattern ){
		if(null==date||date.length()==0) return false ;
		DateFormat df = new  SimpleDateFormat(pattern);
		try {
			Date d = df.parse(date);
			if(null!=d) return true ;
			return false ;
		} catch (ParseException e) {
			log.error(" 日期转换出错，当前输入的日期："+date+" 不满足日期格式", e);
			return false ;
		}
	}
	
	static int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  
	/***
	 * 格式 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static boolean isValidDate(String date) {  
	    try {  
	    	int first = date.indexOf("-");
	        int year = Integer.parseInt(date.substring(0, first));  
	        if (year <= 0)  
	            return false;  
	        int second = date.indexOf("-", first+1);
	        int month = Integer.parseInt(date.substring(first+1, second));  
	        if (month <= 0 || month > 12)  
	            return false;  
	        int day = Integer.parseInt(date.substring(second+1, date.length()));  
	        if (day <= 0 || day > DAYS[month])  
	            return false;  
	        if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {  
	            return false;  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return false;  
	    }  
	    return true;  
	}  
	
	public static final boolean isGregorianLeapYear(int year) {  
	    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);  
	}  
}
