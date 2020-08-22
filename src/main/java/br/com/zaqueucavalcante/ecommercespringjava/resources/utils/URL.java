package br.com.zaqueucavalcante.ecommercespringjava.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Long> stringToLongList(String string) {
		List<Long> longList = new ArrayList<>();
		String[] stringWithoutCommas = string.split(",");
		Long longNumber;
		for (int c = 0; c < stringWithoutCommas.length; c++) {
			longNumber = Long.parseLong(stringWithoutCommas[c]);
			longList.add(longNumber);
		}
		return longList;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static String decodeParam(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
