package com.casic.sensorhub.http;

import android.test.AndroidTestCase;
import com.casic.sensorhub.bean.RestResponse;
import com.casic.sensorhub.bean.UserInfo;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.koushikdutta.async.util.StreamUtility;

import java.io.File;
import java.security.spec.ECField;
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

    final Semaphore sRegisterSucess = new Semaphore(0);
    private ApiClent.ClientCallback registerSuccessCalllback = new ApiClent.ClientCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            String sData = data.toString();
            RestResponse resp = RestResponse.parseJson(sData);
            assertEquals(resp.getMessage(),"注册成功");
            assertEquals(resp.isSuccess(),true);
            sRegisterSucess.release();
        }

        @Override
        public void onFailure(String message)
        {

        }

        @Override
        public void onError(Exception e)
        {

        }
    };

    public void testRegisterSuccess() throws Exception
    {
        ApiClent.register(getContext(), "predator120", "123456", "tobias", registerSuccessCalllback);
        sRegisterSucess.acquire();
    }

    final Semaphore sRegisterFailed = new Semaphore(0);
    private ApiClent.ClientCallback registerFailedCallback = new ApiClent.ClientCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            String sData = data.toString();
            RestResponse resp = RestResponse.parseJson(sData);
            assertEquals(resp.getMessage(),"该账号已注册");
            assertEquals(resp.isSuccess(),false);
            sRegisterFailed.release();

        }

        @Override
        public void onFailure(String message)
        {

        }

        @Override
        public void onError(Exception e)
        {

        }
    };
    public void testrestRegisterFailed() throws Exception
    {
        ApiClent.register(getContext(), "zhangfan", "123456", "predator", registerFailedCallback);
        sRegisterFailed.acquire();
    }


    final Semaphore sPostArticleSuccess = new Semaphore(0);
    private ApiClent.ClientCallback postArticleSuccess = new ApiClent.ClientCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            sPostArticleSuccess.release();
        }

        @Override
        public void onFailure(String message)
        {

        }

        @Override
        public void onError(Exception e)
        {

        }
    };
    public void testPostArticle() throws Exception
    {

        File f = getContext().getFileStreamPath("test.txt");
        StreamUtility.writeFile(f, "hello world");

        ApiClent.postArticle(getContext(), "我的照片", "批示", "1", f,postArticleSuccess);
        sPostArticleSuccess.acquire();

    }

    public void testPostComment() throws Exception
    {

    }

    public void testGetCommetns() throws Exception
    {

    }
}