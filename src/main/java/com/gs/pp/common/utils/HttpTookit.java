package com.gs.pp.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * Created by duronghong on 2015/01/29.
 * Email: drh0534@163.com
 */
public class HttpTookit {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static JSONObject Get(String url, Map<String, String> heads) {
        String result = doGet(url, heads);
        if (!org.springframework.util.StringUtils.isEmpty(result)) {
            return JSONArray.parseObject(result);
        }
        return new JSONObject();
    }

    public static JSONObject Post(String url,Map<String,String> params,Map<String,String> heads){
        String result = doPost(url,params,heads);
        if(!org.springframework.util.StringUtils.isEmpty(result)){
            return JSONArray.parseObject(result);
        }
        return new JSONObject();
    }

    public static String doGet(String url, Map<String, String> heads) {
        return doGet(url, heads, CHARSET);
    }

    public static String doGet(String url, Map<String, String> params, Map<String, String> heads) {
        return doGet(url, params, heads, CHARSET);
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> heads) {
        return doPost(url, params, heads, CHARSET);
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param heads   请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> heads, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpGet httpGet = new HttpGet(url);
            addHttpHeader(heads,httpGet);
            return getResult(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> heads, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            addHttpHeader(heads,httpGet);
            return getResult(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> heads, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            addHttpHeader(heads,httpPost);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            return getResult(httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addHttpHeader(Map<String, String> header, HttpRequestBase httpRequestBase){
        if(!CollectionUtils.isEmpty(header)){
            for(String key:header.keySet()){
                httpRequestBase.setHeader(key,header.get(key));
            }
        }
    }

    private static String getResult(HttpRequestBase httpRequestBase) throws IOException {
        CloseableHttpResponse response = httpClient.execute(httpRequestBase);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpRequestBase.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, CHARSET);
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

}