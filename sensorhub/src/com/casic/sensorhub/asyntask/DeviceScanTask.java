package com.casic.sensorhub.asyntask;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import com.casic.sensorhub.udp.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by admin on 2015/4/30.
 */
public class DeviceScanTask extends BaseAsyncTask<Object, Void, Void>
{
    private  Handler mHandler;
    private Application appContext;
    private Activity myContext;
    private final static String COMMAND="scan";

    public DeviceScanTask(Activity param_0, boolean param_1)
    {
        super(param_0);
        this.myContext = param_0;
        this.appContext = (Application) param_0.getApplicationContext();

    }

    @Override
    protected Void doInBackground(Object... param_0)
    {
        String boradCastId = "192.168.1.104";
      // boradCastId = appContext.getBroadcastIpAddress(this.myContext);

        //construct broadcast address
        /*
        WifiManager wifi = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for(int i =0; i<4; i++){
            quads[i] = (byte) ((broadcast>>i*8)&0xFF);
        }
        InetAddress address = null;
        try {
            address = InetAddress.getByAddress(quads);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */

            int port = ((Integer) param_0[0]).intValue();
            this.mHandler = (Handler) param_0[1];
            new Client(boradCastId, port, this.mHandler).send(COMMAND);

        return null;
    }
}
