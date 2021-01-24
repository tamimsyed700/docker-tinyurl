package ts7.learning.tinyurl.services.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import ts7.learning.tinyurl.TinyUrlWebserverConstant;

public class Utility {

	public static final Pattern REGEX_6_DIGIT_ALPHANUMERIC = Pattern
			.compile(TinyUrlWebserverConstant.REGEX_6_DIGIT_ALPHANUMERIC, Pattern.CASE_INSENSITIVE);

	public static boolean isValidURLAlias(String urlAlias) {
		Matcher matcher = REGEX_6_DIGIT_ALPHANUMERIC.matcher(urlAlias);
		return matcher.find();
	}

	public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}
}
