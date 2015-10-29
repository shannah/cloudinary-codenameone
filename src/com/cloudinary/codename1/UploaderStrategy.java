/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary.codename1;

import com.cloudinary.Cloudinary;
import com.cloudinary.File;
import com.cloudinary.Util;
import com.cloudinary.strategies.AbstractUploaderStrategy;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.cloudinary.codename1.util.CN1String;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;

/**
 *
 * @author shannah
 */
public class UploaderStrategy extends AbstractUploaderStrategy {

	@Override
	public Map callApi(String action, Map<String, Object> params, Map options, Object file) throws IOException {
		// initialize options if passed as null
		if (options == null) {
			options = ObjectUtils.emptyMap();
		}

		boolean returnError = ObjectUtils.asBoolean(options.get("return_error"), false);

		if (options.get("unsigned") == null || Boolean.FALSE.equals(options.get("unsigned"))) {
			uploader.signRequestParams(params, options);
		} else {
			Util.clearEmpty(params);
		}

		String apiUrl = uploader.cloudinary().cloudinaryApiUrl(action, options);

		//ClientConnectionManager connectionManager = (ClientConnectionManager) this.uploader.cloudinary().config.properties.get("connectionManager");
		//HttpClient client = new DefaultHttpClient(connectionManager);

		// If the configuration specifies a proxy then apply it to the client
		//if (uploader.cloudinary().config.proxyHost != null && uploader.cloudinary().config.proxyPort != 0) {
		//	HttpHost proxy = new HttpHost(uploader.cloudinary().config.proxyHost, uploader.cloudinary().config.proxyPort);
		//	client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		//}
                
                //final InputStream[] responseStreamArr = new InputStream[1];
                MultipartRequest request = new MultipartRequest() {

                    //@Override
                    //protected void readResponse(InputStream input) throws IOException {
                    //    super.readResponse(input);
                    //    responseStreamArr[0] = input;
                    //}
                    
                };
                
                request.setUrl(apiUrl);
		//HttpPost postMethod = new HttpPost(apiUrl);
		//postMethod.setHeader("User-Agent", Cloudinary.USER_AGENT + " ApacheHTTPComponents/4.2");
		request.addRequestHeader("User-Agent", Cloudinary.USER_AGENT + " CodenameOne/4.2");
		if (options.get("content_range") != null) {
			request.addRequestHeader("Content-Range", (String) options.get("content_range")); 
		}
		
		//Charset utf8 = Charset.forName("UTF-8");

		//MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		// Remove blank parameters
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (param.getValue() instanceof Collection) {
				for (Object value : (Collection) param.getValue()) {
                                    request.addArgument(param.getKey() + "[]",  ObjectUtils.asString(value));
					//multipart.addPart(param.getKey() + "[]", new StringBody(ObjectUtils.asString(value), utf8));
				}
			} else {
				String value = param.getValue().toString();
				if (StringUtils.isNotBlank(value)) {
					//multipart.addPart(param.getKey(), new StringBody(value, utf8));
                                    request.addArgument(param.getKey(), value);
				}
			}
		}

		if (file instanceof String && !new CN1String((String) file).matches("ftp:.*|https?:.*|s3:.*|data:[^;]*;base64,([a-zA-Z0-9/+\n=]+)")) {
			file = new File((String) file);
		}
		String filename = (String) options.get("filename");
		if (file instanceof File) {
			//multipart.addPart("file", new FileBody((File) file, filename, "application/octet-stream", null));
                        request.addData("file", ((File)file).getPath(), "application/octet-stream");
		} else if (file instanceof String) {
			//multipart.addPart("file", new StringBody((String) file, utf8));
                        request.addData("file", ((String)file).getBytes("UTF-8"), "text/plain");
		} else if (file instanceof byte[]) {
			if (filename == null) filename = "file";
			//multipart.addPart("file", new ByteArrayBody((byte[]) file, filename));
                        request.addData("file", (byte[])file, "text/plain");
		} else if (file == null) {
			// no-problem
		} else {
			throw new IOException("Uprecognized file parameter " + file);
		}
		//postMethod.setEntity(multipart);

		//HttpResponse response = client.execute(postMethod);
                NetworkManager.getInstance().addToQueueAndWait(request);
		//int code = response.getStatusLine().getStatusCode();
                int code = request.getResponseCode();
		//InputStream responseStream = response.getEntity().getContent();
                //InputStream responseStream = responseStreamArr[0];
		//String responseData = StringUtils.read(responseStream);
                String responseData = new String(request.getResponseData(), "UTF-8");

		if (code != 200 && code != 400 && code != 500) {
			throw new RuntimeException("Server returned unexpected status code - " + code + " - " + responseData);
		}

		Map result;
		
		try {
			JSONObject responseJSON = new JSONObject(responseData);
			result= ObjectUtils.toMap(responseJSON);
		} catch (JSONException e) {
			throw new RuntimeException("Invalid JSON response from server " + e.getMessage());
		}
		
		if (result.containsKey("error")) {
			Map error = (Map) result.get("error");
			if (returnError) {
				error.put("http_code", code);
			} else {
				throw new RuntimeException((String) error.get("message"));
			}
		}
		return result;
	}

}