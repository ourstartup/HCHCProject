package com.casic.sensorhub.http;

import android.test.AndroidTestCase;
import com.casic.sensorhub.bean.RestResponse;
import com.casic.sensorhub.bean.UserInfo;

import java.util.concurrent.Semaphore;

public class ApiClentTest extends AndroidTestCase
{


    final Semaphore sLogin = new Semaphore(0);
    private ApiClent.ClientCallback loginCallback = new ApiClent.ClientCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            String sData = data.toString();
            RestResponse resp = RestResponse.parseJson(sData);
            assertEquals(true, resp.isSuccess());
            if (resp.isSuccess())
            {
                UserInfo userInfo = UserInfo.parseJson(resp.getMessage());
                assertEquals("123456", userInfo.getPassword());
                assertEquals("zhangfan", userInfo.getUsername());
                assertEquals("big1", userInfo.getNickName());
                sLogin.release();
            }
            else
            {
            }

        }

        @Override
        public void onFailure(String message)
        {
            sLogin.release();

        }

        @Override
        public void onError(Exception e)
        {
            sLogin.release();
        }
    };

    public void testLogin() throws Exception
    {
        ApiClent.login(getContext(), "zhangfan", "123456", loginCallback);
        sLogin.acquire();
    }

    public void testRegister() throws Exception
    {

    }

    public void testPostArticle() throws Exception
    {

    }

    public void testPostComment() throws Exception
    {

    }

    public void testGetCommetns() throws Exception
    {

    }
}