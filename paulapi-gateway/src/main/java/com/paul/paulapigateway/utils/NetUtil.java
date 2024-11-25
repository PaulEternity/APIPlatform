package com.paul.paulapigateway.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;


public class NetUtil {
    private static final String IP_UNKNOWN = "unknown";
    private static final String IP_LOCAL = "127.0.0.1";
    private static final int IP_SIZE = 15;

    /**
     * 获取请求的真实ip地址
     * @param request
     * @return ip
     */
    public static String getIP(ServerHttpRequest request) {
        HttpHeaders httpHeaders = request.getHeaders();
        //以"x-forwarded-for"字段获取ip
        String ip = httpHeaders.getFirst("x-forwarded-for");
        if (ip == null || ip.isEmpty() || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //不成功就用Proxy-Client-IP
            ip = httpHeaders.getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //还没有就用WL-Proxy-Client-IP
            ip = httpHeaders.getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            //还是没有
            ip = Optional.ofNullable(request.getRemoteAddress())
                    .map(address -> address.getAddress().getHostAddress())
                    .orElse("");
            //获取本机ip
            if(IP_LOCAL.equals(ip)){
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > IP_SIZE){
            int index = ip.indexOf(",");
            if(index > 0){
                ip = ip.substring(0, index);
            }
        }
        return ip;
    }
}
