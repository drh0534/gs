package com.gs.pp.common.utils;


import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;


@SuppressWarnings("all")
public class HttpParamsUtils {

    public static String appendParms(Map<String, Object> data, String url) {
        StringBuffer buf = null;
        if (!StringUtils.isEmpty(url)) {
            buf = new StringBuffer(url);
            Set<String> keySet = data.keySet();
//			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
//				String key = (String) iterator.next();
//				String value = data.get(key)+"";
//				buf.append("&").append(key).append("=").append(value);
//			}
            //TODO update 20151202 duronghong修改
            for (String key : keySet) {
                String value = data.get(key) + "";
                buf.append("&").append(key).append("=").append(value);
            }

            String _url = buf.toString();
            _url = _url.replaceFirst("&", "?");
            return _url;
        }
        return "";
    }


    /**
     * 获取请求的url路径
     */
    public static String getRequestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        StringBuffer buf = new StringBuffer();
        buf.append(request.getRequestURI());
        if (!StringUtils.isEmpty(queryString)) {
            buf.append("?").append(queryString);
        }
        return buf.toString();
    }


    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }


    public static HttpServletResponse getResponse() {
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static String getRequestURI() {
        return getRequest().getRequestURI();
    }

    public static String getRequestURL() {
        return getRequest().getRequestURL().toString();
    }

    public static String getRealIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    public static void main(String[] args) {
        System.out.println(getRealIp());
    }
}
