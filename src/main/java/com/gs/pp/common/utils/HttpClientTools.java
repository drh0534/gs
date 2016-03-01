package com.gs.pp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class HttpClientTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTools.class);

    public static String textformat = "utf8";

    public static final String APPLICATION_JSON = "application/json";

    @SuppressWarnings("unchecked")
    public static List<String> getContent(String url) {
        List<String> content = null;
        try {

            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpGet get = new HttpGet(url);
            get.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instreams = entity.getContent();
                content = convertStreamToStringList(instreams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @SuppressWarnings("unchecked")
    public static List<String> post_comment(HttpPost httpPost) throws Exception {
        List<String> content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            // 构造一个post对象
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                content = HttpClientTools.convertStreamToStringList(instreams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @SuppressWarnings("unchecked")
    public static List<String> getContentByPost(String url) {
        List<String> content = null;
        try {
            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);
            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(url);
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                content = convertStreamToStringList(instreams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String Post(String url, Map<String, Object> heads,
                              Map<String, Object> params) throws Exception {
        try {
            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(url);

            if (heads != null) {
                for (String h : heads.keySet()) {
                    post.setHeader(h, heads.get(h).toString());
                }
            }

            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
            if (params != null) {
                for (String key : params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
                }
            }

            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "utf-8").trim();
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public static String PostJSON(String url, String json,
                                  Map<String, String> heads) {

        int statusCode = 0;
        HttpEntity entity = null;
        String result = null;
        try {
            BasicHttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 2 * 60 * 1000);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(url);

            if (heads != null) {
                for (String h : heads.keySet()) {
                    post.setHeader(h, heads.get(h));
                }
            }

            StringEntity se = new StringEntity(json, "utf-8");
            post.setEntity(se);

            HttpResponse response = client.execute(post);
            statusCode = response.getStatusLine().getStatusCode();
            entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8").trim();
            }
            LOGGER.info(String.format("\n\t请求url:%s,\n\t请求参数:%s,请求头:%s,\n\t返回状态:%d\n\t", url, json, JSON.toJSONString(heads), statusCode));
        } catch (Exception e) {
            LOGGER.error(String.format("\n\t请求url:%s,\n\t请求参数:%s,\n\t请求头:%s,\n\t返回状态:%d\n\t", url, json, JSON.toJSONString(heads), statusCode));
            e.printStackTrace();
        }
        return result;
    }

    public static String Get(String url, Map<String, String> heads) throws Exception {
        HttpGet httpget = new HttpGet(url);
        if (!CollectionUtils.isEmpty(heads)) {
            for (String h : heads.keySet()) {
                httpget.setHeader(h, heads.get(h));
            }
        }
        HttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);// 请求超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);// 读取超时
        HttpResponse response = client.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "utf-8").trim();
        LOGGER.info(String.format("\n\t请求url:%s,\n\t请求头:%s,\n\t返回状态:%d", url, JSON.toJSONString(heads), statusCode));
        return result;

    }

    public static JSONObject parseObject(String url, Map<String, String> heads) throws Exception {
        String result = Get(url, heads);
        if (null != result) {
            return JSONObject.parseObject(result);
        }
        return null;
    }


    public static JSONObject parsePostObject(String url, Map<String, Object> heads,
                                             Map<String, Object> params) throws Exception {
        String result = Post(url, heads, params);
        if (null != result) {
            return JSONObject.parseObject(result);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cpId", "10000000");
        params.put("deviceType", "1");//TODO
        params.put("start", 0 + "");
        params.put("fetchsize", 100 + "");
        String result = Post("http://114.215.142.23:8888/cp_overseaapi/v1/focusData/search", null, params);
        //JSONObject _remote = HttpClientTools.parsePostObject(, null,params);
        System.out.println(result);
    }

    public static JSONArray parseArray(String url, Map<String, String> heads) throws Exception {
        String result = Get(url, heads);
        if (null != result) {
            return JSONObject.parseArray(result);
        }
        return null;
    }

    public static String Upload(String url, File f, String fileName, Map<String, String> heads) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        FileBody fileBody = new FileBody(f);
        StringBody stringBody = null;
        try {
            stringBody = new StringBody(fileName);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("fileData", fileBody);
        reqEntity.addPart("fileDataFileName", stringBody);
        httppost.setEntity(reqEntity);

        // 执行
        return getContext(httpclient, httppost);
    }

    public static String Upload(String url,File f,
                                Map<String, String> heads, Map<String, String> param) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(url);

        MultipartEntity reqEntity = new MultipartEntity();

        if (f != null) {
            FileBody fileBody = new FileBody(f);
            reqEntity.addPart("fileData", fileBody);
        }
        StringBody stringBody = null;
        for (String para : param.keySet()) {
            try {
                stringBody = new StringBody(param.get(para));
                reqEntity.addPart(para, stringBody);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }

        httppost.setEntity(reqEntity);

        // 执行
        return getContext(httpclient, httppost);
    }

    private static String getContext(HttpClient httpClient, HttpPost httpPost) {
        try {
            HttpResponse response = httpClient.execute(httpPost);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                // 显示内容
                if (entity != null) {
                    String context = EntityUtils.toString(entity).trim();
                    System.out.println(context);
                    return context;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List convertStreamToStringList(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                Charset.forName(textformat)));
        Vector list = new Vector();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                list.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
