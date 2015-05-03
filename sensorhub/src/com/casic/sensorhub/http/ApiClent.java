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

    public static void register(Application appContext, String userName, String password, String nickName, final ClientCallback callback)
    {
        JsonObject json = new JsonObject();
        json.addProperty("userName", userName);
        json.addProperty("password", password);
        json.addProperty("nickName", nickName);

        Ion.with(appContext)
                .load(NetUrl.URL_REGISTER)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
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

    public static void postArticle(Application appContext,String title, String body, String userId, File file, final ClientCallback callback)
    {

        Map map = new HashMap<String, List<String>>();
        map.put("title", title);
        map.put("body", body);
        map.put("userId", userId);

        Ion.with(appContext)
                .load(NetUrl.URL_REGISTER)
                .setMultipartParameters(map)
                .setMultipartFile("file", file)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
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

    public static void postComment(Application appContext, String articleId, String message,
                                   String userId, File file, String cid, final ClientCallback callback)
    {
        Map map = new HashMap<String, List<String>>();
        map.put("articleId", articleId);
        map.put("message", message);
        map.put("userId", userId);
        map.put("cid", cid);

        Ion.with(appContext)
                .load(NetUrl.URL_POSTCOMMETN)
                .setMultipartParameters(map)
                .setMultipartFile("file", file)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
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

    public static void getCommetns(Application appContext, String articleId, String userId, String page, String rows, final ClientCallback callback)
    {
        JsonObject json = new JsonObject();
        json.addProperty("articleId", articleId);
        json.addProperty("userId", userId);
        json.addProperty("page", page);
        json.addProperty("rows", rows);

        Ion.with(appContext)
                .load(NetUrl.URL_GETCOMMENTS)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>()
                {
                    @Override
                    public void onCompleted(Exception e, JsonObject result)
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
