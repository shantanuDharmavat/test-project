package com.slidingimages;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 23-Mar-17.
 */
public class NetworkCallHandler {

    public JSONObject retriveMovie(String URL) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(URL);
        JSONObject array;
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            /*Log.e("URL", httpclient.toString());
            Log.e("HTTP POST URL", "");*/
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
//            Log.e("json response",""+EntityUtils.toString(entity));
//            largeLog("json response",EntityUtils.toString(entity));
            array = new JSONObject(EntityUtils.toString(entity));
//            Log.e("json array","a"+array.toString());

            return array;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
