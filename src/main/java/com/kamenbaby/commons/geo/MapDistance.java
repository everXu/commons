package com.kamenbaby.commons.geo;

import java.util.ArrayList;
import java.util.List;

public class MapDistance  {

	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 根据球面求两点距�?double),单位�?
	 * @param lat1  经度1
	 * @param lng1 伟度1
	 * @param lat2 经度2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000 * 1000;
		return s;
	}
	
	/**
	 * 根据球面求两点距�?int),单位�?
	 * 
	 * @param lon1
	 *            经度1
	 * @param lat1
	 *            伟度1
	 * @param lon2
	 *            经度2
	 * @param lat2
	 * @return
	 */
	public static int getDistance(int lon1, int lat1, int lon2, int lat2) {
		double dlon1 = (double) lon1 / 1000000;
		double dlat1 = (double) lat1 / 1000000;
		double dlon2 = (double) lon2 / 1000000;
		double dlat2 = (double) lat2 / 1000000;
		return (int) getDistance(dlon1, dlat1, dlon2, dlat2);
	}
	
	/**
	 * 根据直线求两点距�?单位�?
	 * @param x 经度1
	 * @param y 伟度1
	 * @param x2  经度2
	 * @param y2 伟度2
	 * @return
	 */
	public static int getDistanceByLine(double x, double y, double x2, double y2) {
		return (int) (Math.sqrt(Math.abs(x-x2)*Math.abs(x-x2)+Math.abs(y-y2)*Math.abs(y-y2)) * 102744);//102744深圳经伟度差�?
	}
	

	/**
	 * 在某范围内的车辆 计算方法
	 * @param x 原点经度
	 * @param y 原点伟度
	 * @param listAll �?��的车�?List<String>
	 * @param distanceScrope 某范围内
	 * @return 在某范围内的车辆
	 */
	@SuppressWarnings("unused")
	private static List<String> carInscope(double x, double y, List<String> listAll, int distanceScrope) {

		List<String> listscope = new ArrayList<String>();

		for(int i = 0; i < listAll.size(); i++){
			
			if(listAll.get(i).split(",").length == 2){
				//两点直线距离
				int distanc = getDistanceByLine(x,y, Double.parseDouble(listAll.get(i).split(",")[0]),Double.parseDouble(listAll.get(i).split(",")[1]));
				System.out.println(distanc);
				if(distanc <= distanceScrope) {
					listscope.add(listAll.get(i));
				}
			}

		}
		return listscope;
	}
	

}
