package com.casic.sensorhub.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2015/5/3.
 */
public class RestResponse
{

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
