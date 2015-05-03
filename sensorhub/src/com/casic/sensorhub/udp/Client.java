package com.casic.sensorhub.udp;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import com.casic.sensorhub.bean.ScanDeviceInfo;
import com.koushikdutta.async.AsyncDatagramSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by reweber on 12/20/14.
 */
public class Client
{

    private final InetSocketAddress host;
    private AsyncDatagramSocket asyncDatagramSocket;
    private Handler handler;
    private final static int RECEIVE_DATA=1;
    private final static int CLOSE_CONN = 2;
    private final static int END_CONN = 3;


    public Client(String host, int port, Handler handler)
    {
        this.handler = handler;
        this.host = new InetSocketAddress(host, port);
        setup();
    }

    public Client(InetAddress ip, int port, Handler handler)
    {
        this.handler = handler;
        this.host = new InetSocketAddress(ip,port);
        setup();
    }

    private void setup()
    {
        try
        {
            asyncDatagramSocket = AsyncServer.getDefault().connectDatagram(host);
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        asyncDatagramSocket.setDataCallback(new DataCallback()
        {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb)
            {
                System.out.println("[Client] Received Message " + new String(bb.getAllByteArray()));
                //TODO LIST:封装消息体，由handler传递到UI线程
                String msg = new String(bb.getAllByteArray());
                Log.d("message:", msg);
                String[] results = msg.split(":");
                Log.d("message长度:", ""+results.length);
                ScanDeviceInfo deviceInfo = new ScanDeviceInfo();
                deviceInfo.setDevId(results[0]);
                deviceInfo.setIp(results[1]);
                deviceInfo.setSensorId(results[2]);
                deviceInfo.setCurrentValue(results[3]);

                Message message = new Message();
                message.what = RECEIVE_DATA;
                message.obj = deviceInfo;
                Client.this.handler.sendMessage(message);


            }
        });

        asyncDatagramSocket.setClosedCallback(new CompletedCallback()
        {
            @Override
            public void onCompleted(Exception ex)
            {
                if (ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully closed connection");
                //TODO LIST:封装消息体，由handler传递到UI线程

                Message message = new Message();
                message.what = CLOSE_CONN;
                Client.this.handler.sendMessage(message);

            }
        });

        asyncDatagramSocket.setEndCallback(new CompletedCallback()
        {
            @Override
            public void onCompleted(Exception ex)
            {
                if (ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully end connection");
                //TODO LIST:封装消息体，由handler传递到UI消
                Message message = new Message();
                message.what = END_CONN;
                Client.this.handler.sendMessage(message);

            }
        });
    }

    public void send(String msg)
    {
        asyncDatagramSocket.send(host, ByteBuffer.wrap(msg.getBytes()));

    }

    public void disConnect()
    {
        try
        {
            asyncDatagramSocket.disconnect();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
