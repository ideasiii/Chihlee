package org.iii.chihlee.datatransfer;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import org.iii.more.common.Logs;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;

import static org.iii.chihlee.datatransfer.Controller.STATUS_ROK;


/**
 * Created by Jugo on 2018/7/5
 */
public class SemanticClient
{
    private final String IP = "140.92.142.216";
    private final int PORT = 2310;
    private final int nConnectTimeOut = 5000; // Socket Connect Timeout
    private final int nReceiveTimeOut = 5000; // Socket Read IO Timeout
    private final int SOCKET_CONNECT_SUCCESS = 0;
    
    private Socket socket = null;
    
    public SemanticClient()
    {
    }
    
    public void start()
    {
        stop();
        socket = new Socket();
        
        Logs.showTrace("[SemanticClient] start Socket Created");
        Thread thread = new Thread(new SocketConnect(socket, handler));
        thread.start();
        
    }
    
    
    public void stop()
    {
        if (Controller.validSocket(socket))
        {
            try
            {
                socket.close();
                socket = null;
            }
            catch (Exception e)
            {
                Logs.showError("Socket Close Exception: " + e.getMessage());
            }
        }
    }
    
    public void send(JSONObject jsonObject)
    {
        if (Controller.validSocket(socket))
        {
            Thread thread = new Thread(new SocketSend(socket, handler, jsonObject.toString()));
            thread.start();
        }
    }
    
    private class SocketConnect implements Runnable
    {
        private Socket theSocket = null;
        private Handler theHandler = null;
        
        SocketConnect(Socket socket, Handler handler)
        {
            theSocket = socket;
            theHandler = handler;
        }
        
        @Override
        public void run()
        {
            try
            {
                theSocket.connect(new InetSocketAddress(IP, PORT),
                        nConnectTimeOut);
                theSocket.setSoTimeout(nReceiveTimeOut);
                theHandler.sendEmptyMessage(SOCKET_CONNECT_SUCCESS);
                Logs.showTrace("[WheelPiesClient] SocketConnect : " + theSocket.isConnected());
            }
            catch (Exception e)
            {
                Logs.showError("SocketConnect Exception: " + e.toString());
            }
        }
    }
    
    private class SocketSend implements Runnable
    {
        private Socket theSocket = null;
        private Handler theHandler = null;
        private String theData = null;
        
        SocketSend(Socket socket, Handler handler, String strData)
        {
            theSocket = socket;
            theHandler = handler;
            theData = strData;
        }
        
        @Override
        public void run()
        {
            int nRespon = STATUS_ROK;
            try
            {
                if (theSocket.isConnected())
                {
                    Controller.CMP_PACKET respPacket = new Controller.CMP_PACKET();
                    nRespon = Controller.cmpRequest(Controller.semantic_request, theData,
                            respPacket, theSocket);
                    Logs.showTrace("[SemanticClient] SocketSend Response Code: " + nRespon);
                }
                else
                {
                    Logs.showError("[SemanticClient] SocketSend Socket is not connect");
                }
            }
            catch (Exception e)
            {
                Logs.showError("SocketSend Exception: " + e.toString());
            }
        }
    }
    
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SOCKET_CONNECT_SUCCESS:
                    Logs.showTrace("Socket Connect Success");
                    
                    break;
            }
        }
    };
}
