package com.casic.sensorhub.http;

/**
 * Created by admin on 2015/4/29.
 */
public class NetUrl
{

    public static String URL_LOGIN;
    public static String URL_REGISTER;
    public static String URL_POSTARTICLE;
    public static String URL_POSTCOMMETN;
    public static String URL_GETCOMMENTS;

    static {
        NetUrl.URL_LOGIN = "http://192.168.1.104:9080/iot/rs/user/login";
        NetUrl.URL_REGISTER = "http://serverIP:port/iot/user/register";
        NetUrl.URL_POSTARTICLE = "http://serverIP:port/iot/article/upload";
        NetUrl.URL_POSTCOMMETN = "http://serverIP:PORT/iot/article/postComment";
        NetUrl.URL_GETCOMMENTS = "http://serverIp:port/iot/article/list";


    }
}
