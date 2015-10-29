/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudinary.codename1;

import com.cloudinary.Api;
import com.cloudinary.Api.HttpMethod;
import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.codename1.api.Response;
import com.cloudinary.utils.Base64Coder;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.utils.StringUtils;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;

/**
 *
 * @author shannah
 */
public class ApiStrategy extends com.cloudinary.strategies.AbstractApiStrategy {

    @Override
    public ApiResponse callApi(HttpMethod method, Iterable<String> uri, Map<String, ? extends Object> params, Map options) throws Exception {
		if (options == null)
			options = ObjectUtils.emptyMap();
		
		String prefix = ObjectUtils.asString(options.get("upload_prefix"), ObjectUtils.asString(this.api.cloudinary.config.uploadPrefix, "https://api.cloudinary.com"));
		String cloudName = ObjectUtils.asString(options.get("cloud_name"), this.api.cloudinary.config.cloudName);
		if (cloudName == null)
			throw new IllegalArgumentException("Must supply cloud_name");
		String apiKey = ObjectUtils.asString(options.get("api_key"), this.api.cloudinary.config.apiKey);
		if (apiKey == null)
			throw new IllegalArgumentException("Must supply api_key");
        String apiSecret = ObjectUtils.asString(options.get("api_secret"), this.api.cloudinary.config.apiSecret);
        if (apiSecret == null)
            throw new IllegalArgumentException("Must supply api_secret");

        int timeout = ObjectUtils.asInteger(options.get("timeout"), this.api.cloudinary.config.timeout);

        String apiUrl = StringUtils.join(Arrays.asList(prefix, "v1_1", cloudName), "/");
		for (String component : uri) {
			apiUrl = apiUrl + "/" + component;
		}
                
                final InputStream[] responseInput = new InputStream[1];
                final Map<String,String[]> headers = new HashMap<String,String[]>();
                ConnectionRequest request = new ConnectionRequest() {

                    @Override
                    protected void readResponse(InputStream input) throws IOException {
                        responseInput[0] = input;
                    }

                    @Override
                    protected void readHeaders(Object connection) throws IOException {
                        super.readHeaders(connection); 
                        for (String key : this.getHeaderFieldNames(connection)) {
                            headers.put(key, this.getHeaders(connection, key));
                        }
                    }
                    
                    
                };
                request.setUrl(apiUrl);
		//URIBuilder apiUrlBuilder = new URIBuilder(apiUrl);
		for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
			if (param.getValue() instanceof Iterable) {
				for (String single : (Iterable<String>) param.getValue()) {
                                        request.addArgument(param.getKey() + "[]", single);
					//apiUrlBuilder.addParameter(param.getKey() + "[]", single);
				}
			} else {
				//apiUrlBuilder.addParameter(param.getKey(), ObjectUtils.asString(param.getValue()));
                            request.addArgument(param.getKey(), ObjectUtils.asString(param.getValue()));
			}
		}
		//ClientConnectionManager connectionManager = (ClientConnectionManager) this.api.cloudinary.config.properties.get("connectionManager");

        //DefaultHttpClient client = new DefaultHttpClient(connectionManager);
        if (timeout > 0) {
            
            //HttpParams httpParams = client.getParams();
            //HttpConnectionParams.setConnectionTimeout(httpParams, timeout );
            request.setTimeout(timeout);
            
            //HttpConnectionParams.setSoTimeout(httpParams, timeout );
        }

		//URI apiUri = apiUrlBuilder.build();
		//HttpUriRequest request = null;
		switch (method) {
		case GET:
			//request = new HttpGet(apiUri);
                        request.setHttpMethod("GET");
                        request.setPost(false);
			break;
		case PUT:
                    request.setPost(true);
                    request.setHttpMethod("PUT");
			//request = new HttpPut(apiUri);
			break;
		case POST:
                    request.setPost(true);
                    //request = new HttpPost(apiUri);
                    request.setHttpMethod("POST");
			break;
		case DELETE:
			//request = new HttpDelete(apiUri);
                    request.setPost(true);
                    request.setHttpMethod("DELETE");
                    
			break;
		}
		request.addRequestHeader("Authorization", "Basic " + Base64Coder.encodeString(apiKey + ":" + apiSecret));
		request.addRequestHeader("User-Agent", Cloudinary.USER_AGENT + " CodenameOne/4.2");

		//HttpResponse response = client.execute(request);
                NetworkManager.getInstance().addToQueueAndWait(request);

		//int code = response.getStatusLine().getStatusCode();
                int code = request.getResponseCode();
		//InputStream responseStream = response.getEntity().getContent();
                InputStream responseStream = responseInput[0];
		String responseData = StringUtils.read(responseStream);

		if (code != 200 ) {
			throw new IOException("Server returned unexpected status code - " + code + " - " + responseData);
		}
		Map result;
		
		try {
			JSONObject responseJSON = new JSONObject(responseData);
			result= ObjectUtils.toMap(responseJSON);
		} catch (JSONException e) {
			throw new RuntimeException("Invalid JSON response from server " + e.getMessage());
		}
		return new Response(request, result, headers);
		
	}
    
}
