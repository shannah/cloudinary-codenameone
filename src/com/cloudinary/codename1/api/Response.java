/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary.codename1.api;

import com.cloudinary.Matcher;
import com.cloudinary.Pattern;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.api.RateLimit;
import com.cloudinary.utils.StringUtils;
import com.codename1.io.ConnectionRequest;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shannah
 */
public class Response extends HashMap implements ApiResponse {
	private static final long serialVersionUID = -5458609797599845837L;
	
        ConnectionRequest response;
        Map<String,String[]> headers;
	public Response(ConnectionRequest response, Map result, Map<String,String[]> headers) {
		super(result);
		this.response = response;
                this.headers = headers;
	}

	public ConnectionRequest getRawHttpResponse() {
		return this.response;
	}

	private static final Pattern RATE_LIMIT_REGEX = Pattern
			.compile("X-Feature(\\w*)RateLimit(-Limit|-Reset|-Remaining)");
	private static final String RFC1123_PATTERN = "EEE, dd MMM yyyyy HH:mm:ss z";
	private static final DateFormat RFC1123 = new SimpleDateFormat(
			RFC1123_PATTERN);

	public Map<String, RateLimit> rateLimits() throws java.text.ParseException {
                
		//Header[] headers = this.response.getAllHeaders();
		Map<String, RateLimit> limits = new HashMap<String, RateLimit>();
		for (String header : headers.keySet()) {
			Matcher m = RATE_LIMIT_REGEX.matcher(header);
			if (m.matches()) {
				String limitName = "Api";
				RateLimit limit = null;
				if (!StringUtils.isEmpty(m.group(1))) {
					limitName = m.group(1);
				}
				limit = limits.get(limitName);
				if (limit == null) {
					limit = new RateLimit();
				}
				if (m.group(2).equalsIgnoreCase("-limit")) {
					limit.setLimit(Long.parseLong(headers.get(header)[0]));
				} else if (m.group(2).equalsIgnoreCase("-remaining")) {
					limit.setRemaining(Long.parseLong(headers.get(header)[0]));
				} else if (m.group(2).equalsIgnoreCase("-reset")) {
                                    try {
					limit.setReset(RFC1123.parse(headers.get(header)[0]));
                                    } catch (ParseException ex) {
                                        throw new java.text.ParseException(ex.getMessage(), ex.getErrorOffset());
                                    }
				}
				limits.put(limitName, limit);
			}
		}
		return limits;
	}

	public RateLimit apiRateLimit() throws java.text.ParseException {
		return rateLimits().get("Api");
	}
}
