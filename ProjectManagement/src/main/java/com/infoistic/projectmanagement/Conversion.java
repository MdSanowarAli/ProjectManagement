package com.infoistic.projectmanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;

public class Conversion {

	public final static String PROJECT_ATT = "PROJECT";
	public final static String dir = System.getProperty("user.dir");
	public static String PROJECT_ATT_Path = dir +"/PROJECT";

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't',
			'K', 'e', 'y' };
	
	public static Date nextHour(int min) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 1);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	public static Date parseISO8601Date(String input) throws java.text.ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
		if (input.endsWith("Z")) {
			input = input.substring(0, input.length() - 1) + "GMT-00:00";
		} else {
			int inset = 6;

			String s0 = input.substring(0, input.length() - inset);
			String s1 = input.substring(input.length() - inset, input.length());

			input = s0 + "GMT" + s1;
		}
		return df.parse(input);

	}

	public static String dateToString(Date date) {
		try {
			// SimpleDateFormat fd = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
			SimpleDateFormat fd = new SimpleDateFormat("dd-MMM-yyyy");
			return fd.format(date);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date stringToDate(String date) {
		try {
			return new SimpleDateFormat("dd-MMM-yyyy").parse(date);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date CustomeDate(int d, int MM, int y, int h, int m) {

		try {
			return new SimpleDateFormat("yyyy-M-d hh:mm").parse("" + y + "-" + MM + "-" + d + " " + h + ":" + m);

		} catch (Exception ex) {
			return new Date();
		}

	}

	public static Date CustomeDate(Date date, int h, int m) {

		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			int y = c.get(Calendar.YEAR);
			int MM = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);

			// return new SimpleDateFormat("yyyy-M-d hh:mm").parse("" + y + "-" + MM + "-" +
			// d + " " + h + ":" + m);
			return new SimpleDateFormat("dd/mm/yyyy").parse("" + d + "/" + MM + "/" + d);
		} catch (Exception ex) {
			return new Date();
		}

	}

	/*
	 * public static java.sql.Date getSqlDate(java.util.Date date ){
	 * java.util.Calendar cal = Calendar.getInstance(); cal.setTime(date); return
	 * new java.sql.Date(date.getTime());
	 * 
	 * }
	 */
	public static java.sql.Timestamp getSqlTimestamp(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public static String getClientIpInfo(HttpServletRequest request) {
		String xForwardedForHeader = request.getHeader("X-Forwarded-For");
		String info = "";
		if (xForwardedForHeader == null) {
			info = "IP=" + request.getRemoteAddr();
		} else {
			// As of https://en.wikipedia.org/wiki/X-Forwarded-For
			// The general format of the field is: X-Forwarded-For: client, proxy1, proxy2
			// ...
			// we only want the client
			info = "IP=" + new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
		}
		return info + "--" + request.getHeader("User-Agent");
	}
	
	public static String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}
	
	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
}