package com.bs.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {

	public static String getMD5Pssword(String password) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	public static String getyyyymmddHHmmssTime() {

		Calendar cale = Calendar.getInstance();
		Date tasktime = cale.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(tasktime);
	}

	public static String getyyyymmddHHmmssTime(Date date) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static boolean isValidDouble(String str) {
		boolean convertSuccess = true;
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static boolean isValidNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) <= 0 || str.charAt(i) >= 9) {
				return false;
			}
		}
		return true;
	}

	public static String GetCpuID() {
		try {
			Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			Scanner sc = new Scanner(process.getInputStream());
			sc.next();
			String serial = sc.next();
			return serial;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	   * 得到几天前的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static Date getDateBefore(Date d,int day){
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
	   return now.getTime();
	  }
	  
	  /**
	   * 得到几天后的时间
	   * @param d
	   * @param day
	   * @return
	   */
	  public static Date getDateAfter(Date d,int day){
	   Calendar now =Calendar.getInstance();
	   now.setTime(d);
	   now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
	   return now.getTime();
	  }
}
