package com.shuban.LoginServerShuBan.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	public static boolean checkFishScore(String str){
		return match("\\d+|\\d+-\\d+", str);
	}

	public static String arrayToString(Object... arr) {
		if (arr.length == 0)
			return "";
		return arrayToString(",", arr);
	}

	public static String arrayToString(String... arr) {
		if (arr.length == 0)
			return "";
		return arrayToString(",", arr);
	}

	public static String arrayToString(String sp, Object... arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(sp);
		}
		return sb.delete(sb.length() - sp.length(), sb.length()).toString();
	}

	public static String arrayToString(String sp, String... arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]).append(sp);
		}
		return sb.delete(sb.length() - sp.length(), sb.length()).toString();
	}



	public static String removeFourChar(String content) {
		byte[] b_text;
		try {
			b_text = content.getBytes("utf8");
			for (int i = 0; i < b_text.length; i++) {

				if ((b_text[i] & 0xF8) == 0xF0) {
					for (int j = 0; j < 4; j++) {
						b_text[i + j] = 0x30;
					}
					i += 3;
				}
			}
		} catch (UnsupportedEncodingException e) {
			return "nameerror";
		}

		try {
			return new String(b_text, "utf8").replace("0000", "*");
		} catch (UnsupportedEncodingException e) {
			return "nameerror";
		}
	}

	public static String filterUtf8mb4(String content) {
		byte[] b_text;
		try {
			b_text = content.getBytes("utf8");
			for (int i = 0; i < b_text.length; i++) {
				if ((b_text[i] & 0xF8) == 0xF0) {
					for (int j = 0; j < 4; j++) {
						b_text[i + j] = 0x30;
					}
					i += 3;
				}
			}
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		try {
			return new String(b_text, "utf8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String longToString(Long coin){
		String res= coin+"";
		Double temp_coin= coin.doubleValue();
		if((int)Math.floor(temp_coin/100000000)!= 0){
			res= scaleTwo(temp_coin/100000000)+"亿";
		}else if((int)Math.floor(temp_coin/10000000)!= 0){
			res= scaleTwo(temp_coin/10000000)+"千万";
		}else if((int)Math.floor(temp_coin/10000)!= 0){
			res= scaleTwo(temp_coin/ 10000)+ "万";
		}else{
			res= scaleTwo(temp_coin)+"";
		}
		return res;
	}
	
	public static String formatEmoji(String str) {

		if (str.trim().isEmpty()) {
			return str;
		}
		String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
		String reStr = "";
		Pattern emoji = Pattern.compile(pattern);
		Matcher emojiMatcher = emoji.matcher(str);
		str = emojiMatcher.replaceAll(reStr);
		return str;
	}
	
	private static String scaleTwo(Double f){
		Double res=  new BigDecimal(f).setScale(2, BigDecimal.ROUND_UP).doubleValue();
		String str= "0";
		if(res- Math.floor(res)== 0f)
			str= (int)Math.floor(res)+"";
		else
			str= res+"";
		return str;
	}
	/**
	 * 	判断null 和 ""
	 * @param str
	 * @return
	 * 	true 为null 或""
	 * 	false 不为空
	 */
	public static boolean isEmpty(String str){
		if(str== null || ("").equals(str) ||("null").equals(str)||("NULL").equals(str))
			return true;
		return false;
	}
	
	
	/**
	  * 判断是否为整数  
	  * @param str 传入的字符串  
	  * @return 是整数返回true,否则返回false  
	*/  
	 public static boolean isInteger(String str) {    
	   Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	   return pattern.matcher(str).matches();    
	 }  
	
	public static void main(String[] args) {
		String s= longToString(10000001l);
		System.out.println(s);
	}
}
