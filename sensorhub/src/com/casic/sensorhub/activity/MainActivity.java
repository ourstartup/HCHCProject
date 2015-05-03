package com.casic.sensorhub.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.*;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.Switch;
import android.widget.TextView;
import com.casic.sensorhub.R;
import com.casic.sensorhub.SensorHubApplication;
import com.casic.sensorhub.asyntask.DeviceScanTask;
import com.casic.sensorhub.bean.RestResponse;
import com.casic.sensorhub.bean.ScanDeviceInfo;
import com.casic.sensorhub.bean.UserInfo;
import com.casic.sensorhub.http.ApiClent;
import com.casic.sensorhub.udp.Client;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

	
	//constants
	private static final int FRAGMENT_INDEX = 0;
	private static final int FRAGMENT_STATISTICS = 1;
	private static final int FRAGMENT_OTHER = 2;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    /**
     */

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    break;
                case 1:
                    ScanDeviceInfo info = (ScanDeviceInfo)msg.obj;
                    Log.d("MainAciticy", info.getCurrentValue());
                    Log.d("MainAciticy", info.getDevId());
                    Log.d("MainAciticy", info.getIp());
                    Log.d("MainAciticy", info.getSensorId());
                    break;
                case 2:
                    break;

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        /*

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //TCP client and server (Client will automatically send welcome message after setup and server will respond)


                //UDP client and server (Here the client explicitly sends a message)

                new Client("192.168.1.104", 2013,handler).send("Hello World");
                return null;
            }
        }.execute();
        */

        testLogin();

        /*
        DeviceScanTask deviceScanTask = new DeviceScanTask(this, false);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] =2013;
        arrayOfObject[1] = handler;
        Log.d("MainActivity","进入OnCreat函数");
        deviceScanTask.execute(arrayOfObject);
        */
      
    }

    private ApiClent.ClientCallback callback = new ApiClent.ClientCallback()
    {
        @Override
        public void onSuccess(Object data)
        {
            Log.d("MainActivity:","成功返回");
            String sData = data.toString();
            RestResponse resp = RestResponse.parseJson(sData);
            if (resp.isSuccess())
            {
                Log.d("MainActivity", "登录成功");
                UserInfo userInfo = UserInfo.parseJson(resp.getMessage());
            }
            else
            {
                Log.d("MainActivity", resp.getMessage());
            }

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

    private void testLogin()
    {
        ApiClent.login(getApplicationContext(),"zhangfan","123456",callback);
    }

}
