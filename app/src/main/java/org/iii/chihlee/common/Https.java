package org.iii.chihlee.common;

import org.iii.more.eventlistener.EventListener.Callback;
import org.iii.more.restapiclient.Config.HTTP_DATA_TYPE;
import org.iii.more.restapiclient.Response;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

abstract class Https {
    private static Callback eventListener = null;

    Https() {
    }

    static void setResponseListener(Callback listener) {
        eventListener = listener;
    }

    static void GET(String httpsURL, HTTP_DATA_TYPE http_data_type, HashMap<String, String> parameters, Response response, HashMap<String, String> headers) {
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("id", response.Id);
            jsonResponse.put("code", -1);
            String strParameter = getPostDataString(parameters);
            Logs.showTrace("[Https] GET : URL=" + httpsURL + " Data Type=" + http_data_type.toString() + " Parameter:" + strParameter);
            URL url = new URL(getGetURLString(httpsURL, strParameter));
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            //con.setRequestProperty("Content-length", String.valueOf(strParameter.length()));
            con.setRequestProperty("Content-Type", http_data_type.toString());
            con.setRequestProperty("Cache-Control", "no-cache");
            setHeaders(con, headers);
            con.setDoInput(true);
            response.Code = con.getResponseCode();
            if (response.Code == 200) {
                response.Data = "";

                String line;
                for(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); (line = br.readLine()) != null; response.Data = response.Data + line) {
                    ;
                }
            } else {
                System.out.println("[Https] ERROR HTTP Response Code:" + response.Code);
            }

            jsonResponse.put("code", response.Code);
            jsonResponse.put("data", response.Data);
        } catch (Exception var11) {
            Logs.showTrace("[Https] GET Exception: " + var11.getMessage());
        }

        if (null != eventListener) {
            eventListener.onEvent(jsonResponse);
        }

        Logs.showTrace("[Https] GET Response: " + jsonResponse.toString());
    }

    static void POST(String httpsURL, HTTP_DATA_TYPE http_data_type, HashMap<String, String> parameters, Response response, HashMap<String, String> headers) {
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("id", response.Id);
            jsonResponse.put("code", -1);
            String strParameter = http_data_type == HTTP_DATA_TYPE.JSON ?
                getJSONDataString(parameters):
                getPostDataString(parameters);
            Logs.showTrace("[Https] POST : URL=" + httpsURL + " Data Type=" + http_data_type.toString() + " Parameter:" + strParameter);
            URL url = new URL(httpsURL);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setRequestProperty("Content-length", String.valueOf(strParameter.length()));
            con.setRequestProperty("Content-Type", http_data_type.toString());
            con.setRequestProperty("Cache-Control", "no-cache");
            setHeaders(con, headers);
            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.writeBytes(strParameter);
            output.close();
            response.Code = con.getResponseCode();
            if (response.Code == 200) {
                response.Data = "";

                String line;
                for(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); (line = br.readLine()) != null; response.Data = response.Data + line) {
                    ;
                }
            } else {
                System.out.println("[Https] ERROR HTTP Response Code:" + response.Code);
            }

            jsonResponse.put("code", response.Code);
            jsonResponse.put("data", response.Data);
        } catch (Exception var11) {
            Logs.showTrace("[Https] POST Exception: " + var11.getMessage());
        }

        if (null != eventListener) {
            eventListener.onEvent(jsonResponse);
        }

        Logs.showTrace("[Https] POST Response: " + jsonResponse.toString());
    }

    static void PUT(String httpsURL, HTTP_DATA_TYPE http_data_type, HashMap<String, String> parameters, Response response, HashMap<String, String> headers) {
        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("id", response.Id);
            jsonResponse.put("code", -1);
            String strParameter = http_data_type == HTTP_DATA_TYPE.JSON ?
                getJSONDataString(parameters):
                getPostDataString(parameters);
            Logs.showTrace("[Https] PUT : URL=" + httpsURL + " Data Type=" + http_data_type.toString() + " Parameter:" + strParameter);
            URL url = new URL(httpsURL);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setRequestProperty("Content-length", String.valueOf(strParameter.length()));
            con.setRequestProperty("Content-Type", http_data_type.toString());
            con.setRequestProperty("Cache-Control", "no-cache");
            setHeaders(con, headers);
            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.writeBytes(strParameter);
            output.close();
            response.Code = con.getResponseCode();
            if (response.Code == 200) {
                response.Data = "";

                String line;
                for(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); (line = br.readLine()) != null; response.Data = response.Data + line) {
                    ;
                }
            } else {
                System.out.println("[Https] ERROR HTTP Response Code:" + response.Code);
            }

            jsonResponse.put("code", response.Code);
            jsonResponse.put("data", response.Data);
        } catch (Exception var11) {
            Logs.showTrace("[Https] PUT Exception: " + var11.getMessage());
        }

        if (null != eventListener) {
            eventListener.onEvent(jsonResponse);
        }

        Logs.showTrace("[Https] PUT Response: " + jsonResponse.toString());
    }

    private static void setHeaders(HttpsURLConnection con, HashMap<String, String> headers) {
        if (con == null || headers == null || headers.size() == 0) {
            return;
        }

        Iterator var3 = headers.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, String> entry = (Entry)var3.next();
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private static String getGetURLString(String url, String queryString) {
        StringBuilder result = new StringBuilder(url);

        if (queryString != null && queryString.length() > 0) {
            result.append("?");
            result.append(queryString);
        }

        return result.toString();
    }

    private static String getJSONDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();

        if (params != null && params.containsKey("body")) {
            result.append((String)params.get("body"));
        }
        return result.toString();
    }

    private static String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator var3 = params.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, String> entry = (Entry)var3.next();
            if (first) {
                first = false;
            } else {
                result.append('&');
            }

            result.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
            result.append('=');
            result.append(URLEncoder.encode((String)entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
