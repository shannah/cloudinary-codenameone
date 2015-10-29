package com.cloudinary;

import com.cloudinary.codename1.util.CN1String;
import java.io.UnsupportedEncodingException;

public class SmartUrlEncoder {
	public static String encode(String input) {
		try {
			return new CN1String.Builder(URLEncoder.encode(input, "UTF-8")).replace("%2F", "/").replace("%3A", ":").replace("+", "%20").toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
