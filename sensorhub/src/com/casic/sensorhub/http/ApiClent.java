package com.casic.sensorhub.http;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.casic.sensorhub.bean.RestResponse;
import com.casic.sensorhub.bean.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiClent
{
	public final static String message_error = "服务器连接有问题";

	public interface ClientCallback{
        abstract void onSuccess(Object data);
        abstract void onFailure(String message);
        abstract void onError(Exception e);
    }

	public static void login(Context appContext, String userName, String password, final ClientCallback callback)
    {

        Ion.with(appContext)
                .load(NetUrl.URL_LOGIN)
                .setBodyParameter("userName", userName)
                .setBodyParameter("password", password)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if (e != null)
                        {
                            callback.onError(e);

                            return;
                        }
                        callback.onSuccess(result);
                    }
                });
    }

    public static void register(Context appContext, String userName, String password, String nickName, final ClientCallback callback)
    {

        Ion.with(appContext)
                .load(NetUrl.URL_REGISTER)
                .setBodyParameter("userName", userName)
                .setBodyParameter("password", password)
                .setBodyParameter("nickName", nickName)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if (e != null)
                        {
                            callback.onError(e);

                            return;
                        }
                        callback.onSuccess(result);
                    }
                });

    }

    public static void postArticle(Context appContext,String title, String body, String userId, File file, final ClientCallback callback)
    {

        Ion.with(appContext)
                .load(NetUrl.URL_POSTARTICLE)
                .setMultipartParameter("title", title)
                .setMultipartParameter("body", body)
                .setMultipartParameter("userId", userId)
                .setMultipartFile("file", file)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if (e != null)
                        {
                            callback.onError(e);

                            return;
                        }
                        callback.onSuccess(result);
                    }
                });

    }

    public static void postComment(Context appContext, String articleId, String message,
                                   String userId, File file, String cid, final ClientCallback callback)
    {


        Ion.with(appContext)
                .load(NetUrl.URL_POSTCOMMETN)
                .setMultipartParameter("articleId", articleId)
                .setMultipartParameter("message", message)
                .setMultipartParameter("userId", userId)
                .setMultipartParameter("cid", cid)
                .setMultipartFile("file", file)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if (e != null)
                        {
                            callback.onError(e);

                            return;
                        }
                        callback.onSuccess(result);
                    }
                });
    }

    public static void getComments(Context appContext, String articleId, String userId, String page, String rows, final ClientCallback callback)
    {

        Ion.with(appContext)
                .load(NetUrl.URL_GETCOMMENTS)
                .setBodyParameter("articleId", articleId)
                .setBodyParameter("userId", userId)
                .setBodyParameter("page", page)
                .setBodyParameter("rows", rows)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if (e != null)
                        {
                            callback.onError(e);

                            return;
                        }
                        callback.onSuccess(result);
                    }
                });

    }

    /*
    public static void registerDevice(SensorHubApplication appContext,)
    */


}
