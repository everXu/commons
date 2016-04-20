package com.kamenbaby.commons.geo;



/**
 * 坐标系之间转换
 * 
 * @author <a href="mailto:lizhibo@highand.me">lizhibo</a>
 * 
 *         2014年12月8日 上午10:44:38
 */
public class GPSUtils {
	

	/**
	 * 度分 转为 度 (原始GPS数据为度分格式，百度GPS转换接口API需要度格式数据)
	 * 
	 * @param val
	 *            需要转换的值，去除小数点
	 * @param type
	 *            标识经 lo 纬 la
	 * @return 返回度
	 */
	public static String getDegree(String val, String type) {

		String degreeStr = "0", centStr = "0";

		if ("lo".equals(type)) {

			val = new String((padLeft(val.getBytes(), 9))); // 若不足9位则左补0
			degreeStr = val.substring(0, 3); // 度
			String temp = val.substring(3); // 分
			centStr = temp.substring(0, 2) + "." + temp.substring(2);
		} else if ("la".equals(type)) {

			val = new String((padLeft(val.getBytes(), 8))); // 若不足8位则左补0
			degreeStr = val.substring(0, 2); // 度
			String temp = val.substring(2); // 分
			centStr = temp.substring(0, 2) + "." + temp.substring(2);
		}

		double degree = Double.parseDouble(degreeStr)
				+ Double.parseDouble(centStr) / 60;

		String result = String.valueOf(degree).replace(".", "");

		if ("lo".equals(type)) {

			if (result.length() > 9)
				result = result.substring(0, 9);
			else if (result.length() < 9)
				result = new String(padRight(result.getBytes(), 9));
		} else if ("la".equals(type)) {
			if (result.length() > 8)
				result = result.substring(0, 8);
			else if (result.length() < 8)
				result = new String(padRight(result.getBytes(), 8));
		}

		return result;
	}

	public static byte[] padLeft(byte[] b, int n) {
		byte[] nb = new byte[n];

		if (n <= b.length) {
			System.arraycopy(b, 0, nb, 0, n);
		} else {
			java.util.Arrays.fill(nb, (byte) '0');
			System.arraycopy(b, 0, nb, n - b.length, b.length);
		}
		return nb;
	}

	public static byte[] padRight(byte[] b, int n) {
		byte[] nb = new byte[n];

		java.util.Arrays.fill(nb, (byte) '0');
		System.arraycopy(b, 0, nb, 0, (n < b.length) ? n : b.length);

		return nb;
	}

	public static double x_pi = Math.PI * 3000.0 / 180.0;

	/**
	 * GCJ-02 to BD-09 
	 * @param gg_lat
	 * @param gg_lon
	 * @return lon,lat
	 */
	public static double[] bd_encrypt(double gg_lat, double gg_lon) {
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);

		double[] s = { (z * Math.cos(theta) + 0.0065),
				z * Math.sin(theta) + 0.006 };
		return s;
		/*
		 * bd_lon = z * Math.cos(theta) + 0.0065; bd_lat = z * Math.sin(theta) +0.006;
		 */
	}

	/**
	 * BD-09 to GCJ-02
	 * @param bd_lat
	 * @param bd_lon
	 * @return
	 */
	public static double[] bd_decrypt(double bd_lat, double bd_lon) {
		double x = bd_lon - 0.0065, y = bd_lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		double[] s = { z * Math.cos(theta), z * Math.sin(theta) };
		return s;
		/*
		 * gg_lon = z * Math.cos(theta); gg_lat = z * Math.sin(theta);
		 */
	}

	/**
	 * WGS-84,单位度
	 * WGS-84 to GCJ-02
	 * 
	 * @param wgsLat
	 * @param wgsLon
	 * @return
	 */
	public static double[] wgs84toGCJ02(double wgsLat, double wgsLon) {

		if (outOfChina(wgsLat, wgsLon))
			return new double[] { wgsLon, wgsLat};

		return delta(wgsLat, wgsLon);
	}

	private static double[] delta(double wgsLat, double wgsLon) {

		double a = 6378245.0; // a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double ee = 0.00669342162296594323; // ee: 椭球的偏心率。
		double dLat = transformLat(wgsLon - 105.0, wgsLat - 35.0);
		double dLon = transformLon(wgsLon - 105.0, wgsLat - 35.0);
		double radLat = wgsLat / 180.0 * Math.PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0)
				/ ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

		return new double[] { wgsLon + dLon, wgsLat + dLat };
	}

	private static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	private static double transformLat(double lon, double lat) {
		double ret = -100.0 + 2.0 * lon + 3.0 * lat + 0.2 * lat * lat + 0.1
				* lon * lat + 0.2 * Math.sqrt(Math.abs(lon));
		ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0
				* lon * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0
				* Math.PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(lat / 12.0 * Math.PI) + 320 * Math.sin(lat
				* Math.PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double lon, double lat) {
		double ret = 300.0 + lon + 2.0 * lat + 0.1 * lon * lon + 0.1 * lon
				* lat + 0.1 * Math.sqrt(Math.abs(lon));
		ret += (20.0 * Math.sin(6.0 * lon * Math.PI) + 20.0 * Math.sin(2.0
				* lon * Math.PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(lon * Math.PI) + 40.0 * Math.sin(lon / 3.0
				* Math.PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(lon / 12.0 * Math.PI) + 300.0 * Math.sin(lon
				/ 30.0 * Math.PI)) * 2.0 / 3.0;
		return ret;
	}
	
	/**
	 * GCJ-02 to WGS-84 exactly
	 * @param gcjLat
	 * @param gcjLon
	 * @return double[]{lon,lat}
	 */
	public static double[] gcj20_to_WGS84 (double gcjLat, double gcjLon) {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
		double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
		double wgsLat, wgsLon, i = 0;
        while(true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            double[] tmp = wgs84toGCJ02(wgsLat, wgsLon);
            dLat = tmp[1] - gcjLat;
            dLon = tmp[0] - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;
 
            if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;
 
            if (++i > 10000) break;
        }
        return new double[]{wgsLon,wgsLat};
    }
	

	
	/**
	 * 根据球面求两点距离,单位米
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
	public static double getDistance(double lon1, double lat1, double lon2,double lat2) {

		double distance = 0;
		double lonRes = 102900, latRes = 110000;
		distance = Math.sqrt(Math.abs(lat1 - lat2) * latRes
				* Math.abs(lat1 - lat2) * latRes + Math.abs(lon1 - lon2)
				* lonRes * Math.abs(lon1 - lon2) * lonRes);
		return distance;
		
	}

}
